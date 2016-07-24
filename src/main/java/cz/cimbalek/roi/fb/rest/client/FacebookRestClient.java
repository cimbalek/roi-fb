package cz.cimbalek.roi.fb.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cimbalek.roi.fb.rest.model.Likes;
import cz.cimbalek.roi.fb.rest.model.User;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author cimbalek
 */
@Component
public class FacebookRestClient {

    //TODO: Better logging
    private static final Logger log = LoggerFactory.getLogger(FacebookRestClient.class);

    @Value("${spring.social.facebook.api.request.host}")
    private String url;

    @Value("${spring.social.facebook.api.request.fields}")
    private String fields;

    /* ************** */
    /* PUBLIC METHODS */
    /* ************** */
    /**
     * Retrieves user from Facebook.
     *
     * @param id id of user to be retrieved.
     * @param token valid token making user's data accessible.
     * @return User data structure representing received data in JSON.
     * @throws FacebookCommunicationException if there are any communication issues.
     * @throws FacebookRequestFormatException if Facebook refuses request.
     */
    public User getUser(String id, String token) throws FacebookCommunicationException, FacebookRequestFormatException {

        User user = sendRequestAndProcessResponse(url + id + fields + token, User.class);

        log.debug("Obtained User: " + user.toString());

        Likes likes;
        String next = user.getLikes().getPaging().getNext();
        while (StringUtils.isNotBlank(next)) {
            likes = sendRequestAndProcessResponse(next, Likes.class);
            next = likes.getPaging().getNext();
            user.getLikes().getData().addAll(likes.getData());
            log.debug(String.format("Adding %d likes to user.", (likes.getData() == null ? 0 : likes.getData().size())));
        }

        int likesCount = FacebookClientUtils.getUserLikesCount(user);

        log.info(String.format("User id=%s with %d likes successfully retrieved.", user.getId(), likesCount));

        return user;
    }

    /* *************** */
    /* PRIVATE METHODS */
    /* *************** */
    /**
     * Sends HTTP request to specified URL and unpacks response.
     *
     * @param <T> Root class to which shall be response unpacked.
     * @param url Url where to send HTTP request.
     * @param result Root class to which shall be response unpacked.
     * @return instance of root class containing data received in response.
     * @throws FacebookCommunicationException if there are any communication issues or if unexpected response code is received.
     * @throws FacebookRequestFormatException if Facebook refuses request.
     */
    private <T> T sendRequestAndProcessResponse(String url, Class<T> result) throws FacebookCommunicationException, FacebookRequestFormatException {

        HttpResponse response = null;
        try {

            HttpGet request = prepareGetRequest(url);
            try {

                response = executeRequest(request);

                int code = response.getStatusLine().getStatusCode();

                if (code == 200) {
                    log.info("Request Successful.");
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.getEntity().getContent(), result);
                } else if (code == 400) {
                    log.error("Request Unsuccessfull. Bad Request. Probably not valid combination of user ID and access token, or no permission to access user's data.");
                    throw new FacebookRequestFormatException("Bad request. Please, make sure you have entered valid combination of user ID and access token and that user permited you access.");
                } else {
                    log.error("Unexpected response from Facebook: " + response.getStatusLine());
                    throw new FacebookCommunicationException("Facebook returned unexpected response: " + response.getStatusLine());
                }

            } catch (IOException ex) {
                log.error("Executing request for URL [" + url + "] failed", ex);
                throw new FacebookCommunicationException("Executing request for URL [" + url + "] failed");
            } finally {
                request.releaseConnection();
            }

        } finally {
            HttpClientUtils.closeQuietly(response);
        }

    }

    /**
     * Prepares HTTP GET request for specified url.
     *
     * @param url url to which request shall be sent.
     * @return Prepared HTTP GET request
     */
    private HttpGet prepareGetRequest(String url) {
        HttpGet getRequest = new HttpGet(url);

        getRequest.addHeader("Host", "graph.facebook.com");
        getRequest.addHeader("Accept-Charset", "UTF-8");
        getRequest.addHeader("Accept-Language", "en");

        return getRequest;
    }

    /**
     * Executes HTTPUriReques request.
     *
     * @param request request to be executed.
     * @return HTTP response returned for request.
     * @throws IOException if request fails.
     */
    private HttpResponse executeRequest(HttpUriRequest request) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        log.debug("Sending request: " + request + Arrays.deepToString(request.getAllHeaders()));

        HttpResponse response = client.execute(request);

        log.debug("Obtained response: " + response.getStatusLine() + ". Entity: " + response.getEntity().toString(), response);

        return response;
    }

}
