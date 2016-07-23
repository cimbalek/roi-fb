package cz.cimbalek.roi.fb.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cimbalek.roi.fb.rest.model.Likes;
import cz.cimbalek.roi.fb.rest.model.User;
import java.io.IOException;
import java.net.URLEncoder;
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
//
//    public boolean checkConnection() throws FacebookCommunicationException {
//        User user;
//        try {
//            user = getUser();
//        } catch (FacebookCommunicationException ex) {
//            throw new FacebookCommunicationException("Connection to Facebook unsuccesssful.");
//        }
//        if (user == null) {
//            throw new FacebookCommunicationException("Connection to Facebook unsuccesssful..");
//        }
//        return true;
//    }
    public User getUser(String id, String token) throws FacebookCommunicationException {
        User user = null;
        try {
            user = sendRequestAndProcessResponse(url + id + fields + token, User.class);

            log.info("Obtained User: " + user.toString());

            Likes likes;
            String next = user.getLikes().getPaging().getNext();
            while (StringUtils.isNotBlank(next)) {
                likes = sendRequestAndProcessResponse(next, Likes.class);
                next = likes.getPaging().getNext();
                user.getLikes().getData().addAll(likes.getData());
                log.info("Adding likes to user: " + likes.toString());
            }
        } catch (Exception ex) {
            log.error("Obtaining user failed", ex);
        }

        return user;
    }

    /* *************** */
    /* PRIVATE METHODS */
    /* *************** */
    private <T> T sendRequestAndProcessResponse(String url, Class<T> result) throws FacebookCommunicationException {

        HttpResponse response = null;
        try {

            HttpGet request = prepareGetRequest(url);
            try {

                response = executeRequest(request);

                int code = response.getStatusLine().getStatusCode();

                if (code == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.getEntity().getContent(), result);

                } else {
                    return null;
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

    private HttpGet prepareGetRequest(String url) {
        HttpGet getRequest = new HttpGet(url);

        getRequest.addHeader("Host", "graph.facebook.com");
        getRequest.addHeader("Accept-Charset", "UTF-8");
        getRequest.addHeader("Accept-Language", "en");

        return getRequest;
    }

    private HttpResponse executeRequest(HttpUriRequest request) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        log.debug("Sending request: " + request + Arrays.deepToString(request.getAllHeaders()));

        HttpResponse response = client.execute(request);

        log.debug("Obtained response: " + response.getStatusLine() + ". Entity: " + response.getEntity(), response);

        return response;
    }

}
