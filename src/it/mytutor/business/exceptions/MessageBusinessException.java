package it.mytutor.business.exceptions;

public class MessageBusinessException extends Exception {

    public MessageBusinessException() {
        super();
    }

    public MessageBusinessException(String message) {
        super(message);
    }

    public MessageBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageBusinessException(Throwable cause) {
        super(cause);
    }

    protected MessageBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
