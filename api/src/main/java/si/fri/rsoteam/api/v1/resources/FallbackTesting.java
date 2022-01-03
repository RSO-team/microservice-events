package si.fri.rsoteam.api.v1.resources;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rsoteam.services.beans.EventsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("/fallback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FallbackTesting {
    private Logger log = LogManager.getLogger(EventsResource.class.getName());

    @Inject
    private EventsBean eventsBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/string/{id}")
    @Operation(summary = "Receive given integer", description = "Receive given integer if it is 0, then return error.")
    @APIResponses({
            @APIResponse(
                    description = "Return string",
                    responseCode = "200"
            )
    })
    @Log(LogParams.METRICS)
    public Response getSomeString(@PathParam("id") Integer id) throws Exception {
        return Response.ok(eventsBean.getSomeString(id)).build();
    }
    @GET
    @Path("/sleep/{id}")
    @Operation(summary = "Sleep for given amount", description = "If requested sleep is longer than allowed, execute fallback.")
    @APIResponses({
            @APIResponse(
                    description = "Return string",
                    responseCode = "200"
            )
    })
    @Log(LogParams.METRICS)
    public Response getLongRequest(@PathParam("id") Integer id) throws Exception {
        return Response.ok(eventsBean.sleepFor(id)).build();
    }
}
