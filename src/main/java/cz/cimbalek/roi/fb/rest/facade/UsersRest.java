package cz.cimbalek.roi.fb.rest.facade;

import cz.cimbalek.roi.fb.db.manager.UserManager;
import cz.cimbalek.roi.fb.rest.client.FacebookCommunicationException;
import cz.cimbalek.roi.fb.rest.client.FacebookRestClient;
import cz.cimbalek.roi.fb.rest.model.User;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author cimbalek
 */
@Path("users")
public class UsersRest {

    private static final Logger log = LoggerFactory.getLogger(UsersRest.class);

    @Autowired
    private FacebookRestClient client;

    @Autowired
    private UserManager userManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response downloadUserData(UserDataRequest request) {

        if ((request == null)
            || StringUtils.isBlank(request.getUserId())
            || StringUtils.isBlank(request.getAccessToken())) {
            return Response.status(422).entity("Invalid request. Please make sure that request contains ID of requested user and access token.").build();
        }

        User user;

        try {
            user = client.getUser(request.getUserId(), request.getAccessToken());
        } catch (FacebookCommunicationException ex) {
            log.error(String.format("Obtaining data from Facebook for user id=%s has failed.", request.getUserId()), ex);
            return Response.status(503).entity(String.format("Obtaining data from Facebook for user id=%s has failed.", request.getUserId())).build();
        }

        try {
            userManager.persist(user);
        } catch (Throwable ex) {
            log.error(String.format("Storing user id=%s to database failed.", request.getUserId()), ex);
            return Response.serverError().entity(String.format("Storing user id=%s to database failed.", request.getUserId())).build();
        }

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUserData(@PathParam(value = "id") String id) {
        return Response.ok(userManager.getUser(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/likes")
    public Response getUserLikes(@PathParam(value = "id") String id) {

        return Response.ok(userManager.getUserLikes(id)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUserData(@PathParam(value = "id") String id) {

        userManager.delete(id);

        return Response.ok().build();
    }
}
