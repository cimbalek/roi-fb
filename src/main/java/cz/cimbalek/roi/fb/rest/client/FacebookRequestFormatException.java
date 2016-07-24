package cz.cimbalek.roi.fb.rest.client;

/**
 *
 * @author cimbalek
 */
public class FacebookRequestFormatException extends Exception {

    public FacebookRequestFormatException() {
    }

    public FacebookRequestFormatException(String message) {
        super(message);
    }

    public FacebookRequestFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacebookRequestFormatException(Throwable cause) {
        super(cause);
    }

}
