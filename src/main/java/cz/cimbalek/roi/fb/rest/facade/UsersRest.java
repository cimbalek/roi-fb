/* (c) GreeNova services 2015 */
package cz.cimbalek.roi.fb.rest.facade;

import ch.qos.logback.core.net.server.Client;
import cz.cimbalek.roi.fb.rest.client.FacebookRestClient;
import cz.cimbalek.roi.fb.rest.model.UserDataRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cimbalek
 */
@Path("users")
public class UsersRest {

    @Autowired
    private FacebookRestClient client;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response downloadUserData(UserDataRequest request) {

        return Response.accepted().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUserData(@PathParam(value = "id") String id) {

        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/likes")
    public Response getUserLikes(@PathParam(value = "id") String id) {

        return null;
    }

    @DELETE
    @Path("{id}")
    public Response deleteUserData(@PathParam(value = "id") String id) {

        return null;
    }
}
