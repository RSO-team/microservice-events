package si.fri.rsoteam.services.beans;

import si.fri.rsoteam.models.entities.Event;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@RequestScoped
public class EventsBean {
    private Logger log = Logger.getLogger(EventsBean.class.getName());

    @Inject
    private EntityManager em;
    // TODO Implement CRUD and other required methods

    /**
     * <p> Queries the database and returns a specific event based on given id. </p>
     *
     * @param id THe id of the wanted event.
     * @return Response object containing the requested event, or empty with the NOT_FOUND status.
     */
    @GET
    @Path("/{id}")
    public Response getEvent(@PathParam("id") Integer id){
        Event event = em.find(Event.class, id);
        if(event == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(event).build();
    }

    /**
     * <p> Insert the provided book into the database.</p>
     * @param event The event object that will be created.
     * @return Response object containing created event object.
     */
    @POST
    public Response createEvent(Event event){
        this.beginTx();
        em.persist(event);
        this.commitTx();
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    /**
     * <p> Update event with given id. </p>
     * @param id Id of object we want to update.
     * @param event si.fri.rsoteam.models.entities.Event with new properties.
     * @return Response object containing updated event object.
     * */
//
//    @POST
//    private Response updateEvent(si.fri.rsoteam.models.entities.Event event, Integer id){
//        this.beginTx();
//        si.fri.rsoteam.models.entities.Event oldEvent = em.find(si.fri.rsoteam.models.entities.Event.class, id);
//        oldEvent.setDuration(event.getDuration());
//        oldEvent.setCreatorId(event.getCreatorId());
//        oldEvent.setEventScope(event.getEventScope());
//        oldEvent.setStartsAt(event.getStartsAt());
//        em.persist(oldEvent);
//        this.commitTx();
//        return Response.ok(oldEvent).build();
//    }
    /**
     * <p> Remove given object from database if it exists. </p>
     * @param event  si.fri.rsoteam.models.entities.Event to remove from database.
     * @return Response object with status gone if deletion was successful, else returns not found.
     * */
    private Response deleteEvent(Event event){
        if(em.find(Event.class, event.getId()) != null) {
            this.beginTx();
            em.remove(event);
            this.commitTx();
            return Response.status(Response.Status.GONE).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
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
