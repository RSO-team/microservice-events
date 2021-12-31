package si.fri.rsoteam.api.v1.resources;

import si.fri.rsoteam.lib.dtos.EventDto;
import si.fri.rsoteam.services.beans.EventsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsResource {

    private Logger log = Logger.getLogger(EventsResource.class.getName());

    @Inject
    private EventsBean eventsBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Operation(summary = "Return all events.", description = "Returns the list of all the events.")
    @APIResponses({
            @APIResponse(
                    description = "Successfully returned all events",
                    responseCode = "200",
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )
    })
    public Response getObjects() {
        return Response.ok(eventsBean.getList()).build();
    }

    @GET
    @Path("/{objectId}")
    @Operation(summary = "Return event by id.", description = "Returns specific event by id.")
    @APIResponses({
            @APIResponse(
                    description = "Successfully returned specific event",
                    responseCode = "200",
            )
    })
    public Response getEventById(@PathParam("objectId") Integer id) {
        return Response.ok(eventsBean.getEvent(id)).build();
    }

    @POST
    @Operation(summary = "Create new event.", description = "Create new event.")
    @APIResponses({
            @APIResponse(
                    description = "Successfully created new event",
                    responseCode = "201",
            )
    })
    public Response createEvent(EventDto eventDto) {
        return Response.status(201).entity(eventsBean.createEvent(eventDto)).build();
    }

    @PUT
    @Path("{objectId}")
    @Operation(summary = "Update event.", description = "Update specific event by id.")
    @APIResponses({
            @APIResponse(
                    description = "Successfully updated new event",
                    responseCode = "201",
            )
    })
    public Response updateEvent(@PathParam("objectId") Integer id, EventDto eventDto) {
        return Response.status(201).entity(eventsBean.updateEvent(eventDto, id)).build();
    }

    @DELETE
    @Path("{objectId}")
    @Operation(summary = "Delete an event.", description = "Delete specific event.")
    @APIResponses({
            @APIResponse(
                    description = "Successfully deleted event",
                    responseCode = "204",
            )
    })
    public Response deleteEvent(@PathParam("objectId") Integer id) {
        eventsBean.deleteEvent(id);
        return Response.status(204).build();
    }
}