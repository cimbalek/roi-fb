package cz.cimbalek.roi.fb.rest.facade;

import cz.cimbalek.roi.fb.db.manager.UserManager;
import cz.cimbalek.roi.fb.db.model.FanPageEntity;
import cz.cimbalek.roi.fb.db.model.UserEntity;
import cz.cimbalek.roi.fb.rest.client.FacebookRequestFormatException;
import cz.cimbalek.roi.fb.rest.client.FacebookRestClient;
import cz.cimbalek.roi.fb.rest.model.User;
import cz.cimbalek.roi.fb.rest.model.UserDataRequest;
import java.util.List;
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
    private FacebookRestClient facebookClient;

    @Autowired
    private UserManager userManager;

    /**
     * Requests user from Facebook and saves his data to the DB.
     *
     * @param request JSON containing ID of user to be retrieved from Facebook as "userId" and access_token as "accessToken".
     * @return 200 if successful; 422 if JSON is malformed; 400 if Facebook refuses request for user; 503 if there are any connection errors.
     */
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
            user = facebookClient.getUser(request.getUserId(), request.getAccessToken());
        } catch (FacebookRequestFormatException ex) {
            log.error(ex.getMessage(), ex);

            return Response.status(400).entity(
                String.format("Request for user id=%s with access_token=%s unsuccessful. %s",
                    request.getUserId(),
                    request.getAccessToken(),
                    ex.getMessage()
                )).build();
        } catch (Exception ex) {
            log.error(String.format("Obtaining data from Facebook for user id=%s has failed.", request.getUserId()), ex);

            return Response.status(503).entity(String.format("Obtaining data from Facebook for user id=%s has failed.", request.getUserId())).build();
        }

        if (user == null) {
            return Response.status(503).entity("Facebook returned invalid data.").build();
        }

        try {
            userManager.persist(user);
        } catch (Throwable ex) {
            log.error(String.format("Storing user id=%s to database failed.", request.getUserId()), ex);
            return Response.serverError().entity(String.format("Storing user id=%s to database failed.", request.getUserId())).build();
        }

        return Response.ok(String.format("User id=%s successfuly retrieved and stored into DB", user.getId())).build();
    }

    /**
     * Yields data of Facebook user (no likes included). Data are retrieved from DB, no requests to Facebook API - data could be outdated.
     *
     * @param id Id of user whose data shall be retrieved.
     * @return 200 with id, name, gender and Facebook profile picture url of the user.; 204 if no such user exists in DB.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUserData(@PathParam(value = "id") String id) {
        UserEntity user = userManager.getUser(id);

        if (user == null) {
            return Response.noContent().build();
        }

        return Response.ok(user).build();
    }

    /**
     * Yields likes of user requested. Data are retrieved from DB, no requests to Facebook API - data could be outdated.
     *
     * @param id Id of user whose likes shall be retrieved.
     * @return 200 with List of likes of the requested user. Each like contains id, name, description and Facebook profile picture url.; 204 if no such user
     * exists in DB.
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/likes")
    public Response getUserLikes(@PathParam(value = "id") String id) {
        UserEntity user = userManager.getUserWithLikes(id);
        if (user == null) {
            return Response.noContent().build();
        }

        List<FanPageEntity> likes = user.getPages();

        return Response.ok(likes).build();
    }

    /**
     * Deletes user and his/her likes from the DB. Pages liked by other users remain untouched, only connections with deleted user are removed.
     *
     * @param id Id of user to be deleted.
     * @return 200 if successful; 204 if no such user exists in DB.
     */
    @DELETE
    @Path("{id}")
    public Response deleteUserData(@PathParam(value = "id") String id) {

        UserEntity user = userManager.getUser(id);

        if (user == null) {
            return Response.noContent().build();
        }

        userManager.delete(id);

        return Response.ok().build();
    }
}
