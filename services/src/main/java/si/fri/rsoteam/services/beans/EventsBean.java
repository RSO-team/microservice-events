package si.fri.rsoteam.services.beans;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rsoteam.lib.dtos.EventDto;
import si.fri.rsoteam.lib.dtos.InviteeDto;
import si.fri.rsoteam.lib.dtos.UserDto;
import si.fri.rsoteam.models.entities.EventEntity;
import si.fri.rsoteam.services.config.RestConfig;
import si.fri.rsoteam.services.mappers.EventMapper;
import si.fri.rsoteam.services.mappers.InviteeMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class EventsBean {
    private Logger log = LogManager.getLogger(EventsBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    @Metric(name = "invitees_per_event")
    Histogram histogram;

    @Inject
    private RestConfig restConfig;

    @Inject
    private InviteeBean inviteeBean;

    @Inject
    @DiscoverService(value = "basketball-users")
    private URL userServiceUrl;

    private Client httpClient;

    @PostConstruct
    private void init() throws URISyntaxException {
        httpClient = ClientBuilder.newClient();
    }


    /**
     * <p> Queries the database and returns a specific event based on given id. </p>
     *
     * @param id THe id of the wanted event.
     * @return Response object containing the requested event, or empty with the NOT_FOUND status.
     */
    @Timed
    public EventDto getEvent(Integer id) {
        return EventMapper.entityToDto(em.find(EventEntity.class, id));
    }


    @Timed
    public List<EventDto> getList() {
        List<EventEntity> eventEntityList = em.createNamedQuery("User.getAll", EventEntity.class).getResultList();

        if (userServiceUrl != null) {
            try {
                return eventEntityList.stream().map(eventEntity -> {
                    List<InviteeDto> inviteeDtos = eventEntity.getinvitees().stream().map(i -> {
                        String host = String.format("%s://%s:%s/v1/users/%d",
                                userServiceUrl.getProtocol(),
                                userServiceUrl.getHost(),
                                userServiceUrl.getPort(),
                                i.getUserId());
                        UserDto response = httpClient
                                .target(host)
                                .request()
                                .header("apiToken", restConfig.getApiToken())
                                .get(new GenericType<>() {
                                });
                        return InviteeMapper.userDtoToDto(response);
                    }).collect(Collectors.toList());
                    return EventMapper.entityToDtoWithInvitees(eventEntity, inviteeDtos);
                }).collect(Collectors.toList());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return eventEntityList.stream().map(EventMapper::entityToDto).collect(Collectors.toList());
    }

    @CircuitBreaker(requestVolumeThreshold = 3)
    @Fallback(fallbackMethod = "notAValidString")
    public String getSomeString(Integer id) throws Exception {
        if (id == 0) {
            throw new Exception("There is no zero indices");
        }
        return String.format("I'm okay with this integer: %d", id);
    }

    public String notAValidString(Integer id) {
        return "0 is not used for SQL database ids";
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Fallback(fallbackMethod = "haveBeenSleepingForTooLong")
    public String sleepFor(Integer id) {
        try {
            Thread.sleep(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "I survived";
    }

    public String haveBeenSleepingForTooLong(Integer id) {
        return "You have been sleeping for too long";
    }

    public EventDto createEvent(EventDto eventDto) {
        EventEntity eventEntity = EventMapper.dtoToEntity(eventDto);
        this.beginTx();
        em.persist(eventEntity);
        this.commitTx();
        histogram.update(eventDto.invitees.size());
        return EventMapper.entityToDto(eventEntity);
    }


    public EventDto updateEvent(EventDto eventDto, Integer id) {
        this.beginTx();

        EventEntity eventEntity = em.find(EventEntity.class, id);
        eventEntity.setDuration(eventDto.duration);
        eventEntity.setCreatorId(eventDto.creatorId);
        eventEntity.setEventScope(EventEntity.EventScope.valueOf(eventDto.eventScope));
        eventEntity.setStartsAt(eventDto.startsAt);
        eventEntity.setInvitees(eventDto.invitees.stream().map(InviteeMapper::dtoToEntity).collect(Collectors.toList()));
        eventEntity.getinvitees().forEach(inviteeEntity -> inviteeEntity.setEvent(eventEntity));


        em.persist(eventEntity);
        this.commitTx();

        return EventMapper.entityToDto(eventEntity);
    }


    public void deleteEvent(Integer id) {
        EventEntity eventEntity = em.find(EventEntity.class, id);
        if (eventEntity != null) {
            eventEntity.getinvitees().forEach(inviteeEntity -> inviteeBean.deleteInvitee(inviteeEntity.getId()) );
            this.beginTx();
            em.remove(eventEntity);
            this.commitTx();
        }
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
