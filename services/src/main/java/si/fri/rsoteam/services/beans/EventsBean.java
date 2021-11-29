package si.fri.rsoteam.services.beans;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rsoteam.models.entities.EventEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
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
    @Timed
    public Response getEvent(@PathParam("id") Integer id){
        EventEntity eventEntity = em.find(EventEntity.class, id);
        if(eventEntity == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(eventEntity).build();
    }

    /**
     * <p> Insert the provided book into the database.</p>
     * @param eventEntity The event object that will be created.
     * @return Response object containing created event object.
     */
    @POST
    public Response createEvent(EventEntity eventEntity){
        this.beginTx();
        em.persist(eventEntity);
        this.commitTx();
        return Response.status(Response.Status.CREATED).entity(eventEntity).build();
    }

    /**
     * <p> Update event with given id. </p>
     * @param id Id of object we want to update.
     * @param eventEntity si.fri.rsoteam.models.entities.Event with new properties.
     * @return Response object containing updated event object.
     */

    @PUT
    public Response updateEvent(EventEntity eventEntity, Integer id){
        this.beginTx();
        EventEntity oldEventEntity = em.find(EventEntity.class, id);
        oldEventEntity.setDuration(eventEntity.getDuration());
        oldEventEntity.setCreatorId(eventEntity.getCreatorId());
        oldEventEntity.setEventScope(eventEntity.getEventScope());
        oldEventEntity.setStartsAt(eventEntity.getStartsAt());
        em.persist(oldEventEntity);
        this.commitTx();
        return Response.ok(oldEventEntity).build();
    }

    /**
     * <p> Remove given object from database if it exists. </p>
     * @param eventEntity  si.fri.rsoteam.models.entities.Event to remove from database.
     * @return Response object with status gone if deletion was successful, else returns not found.
     */
    @POST
    public Response deleteEvent(EventEntity eventEntity){
        if(em.find(EventEntity.class, eventEntity.getId()) != null) {
            this.beginTx();
            em.remove(eventEntity);
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
