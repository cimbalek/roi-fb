package cz.cimbalek.roi.fb.rest.client;

/**
 *
 * @author cimbalek
 */
class FacebookCommunicationException extends Exception {

    public FacebookCommunicationException() {
    }

    public FacebookCommunicationException(String message) {
        super(message);
    }

    public FacebookCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacebookCommunicationException(Throwable cause) {
        super(cause);
    }

}
