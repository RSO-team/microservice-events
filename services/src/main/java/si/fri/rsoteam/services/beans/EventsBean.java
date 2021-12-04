package si.fri.rsoteam.services.beans;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rsoteam.lib.dtos.EventDto;
import si.fri.rsoteam.models.entities.EventEntity;
import si.fri.rsoteam.services.mappers.EventMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class EventsBean {
    private Logger log = Logger.getLogger(EventsBean.class.getName());

    @Inject
    private EntityManager em;

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

    /**
     * <p> Queries the database and returns a specific event based on given id. </p>
     *
     * @param id THe id of the wanted event.
     * @return Response object containing the requested event, or empty with the NOT_FOUND status.
     */
    @Timed
    public List<EventDto> getList() {
        List<EventEntity> eventEntityList = em.createNamedQuery("User.getAll", EventEntity.class).getResultList();
        return eventEntityList.stream().map(EventMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * <p> Insert the provided book into the database.</p>
     *
     * @param eventEntity The event object that will be created.
     * @return Response object containing created event object.
     */
    public EventDto createEvent(EventDto eventDto) {
        EventEntity eventEntity = EventMapper.dtoToEntity(eventDto);
        this.beginTx();
        em.persist(eventEntity);
        this.commitTx();

        return EventMapper.entityToDto(eventEntity);
    }

    /**
     * <p> Update event with given id. </p>
     *
     * @param id          Id of object we want to update.
     * @param eventEntity si.fri.rsoteam.models.entities.Event with new properties.
     * @return Response object containing updated event object.
     */
    public EventDto updateEvent(EventDto eventDto, Integer id) {
        this.beginTx();

        EventEntity eventEntity = em.find(EventEntity.class, id);
        eventEntity.setDuration(eventDto.duration);
        eventEntity.setCreatorId(eventDto.creatorId);
        eventEntity.setEventScope(EventEntity.EventScope.valueOf(eventDto.eventScope));
        eventEntity.setStartsAt(eventDto.startsAt);

        em.persist(eventEntity);
        this.commitTx();

        return EventMapper.entityToDto(eventEntity);
    }

    /**
     * <p> Remove given object from database if it exists. </p>
     *
     * @param eventEntity si.fri.rsoteam.models.entities.Event to remove from database.
     * @return Response object with status gone if deletion was successful, else returns not found.
     */
    public void deleteEvent(Integer id) {
        if (em.find(EventEntity.class, id) != null) {
            this.beginTx();
            em.remove(id);
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
