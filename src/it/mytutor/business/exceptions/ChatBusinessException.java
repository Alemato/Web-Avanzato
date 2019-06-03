package it.mytutor.business.exceptions;

public class ChatBusinessException extends Exception {

    public ChatBusinessException() {
        super();
    }

    public ChatBusinessException(String message) {
        super(message);
    }

    public ChatBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatBusinessException(Throwable cause) {
        super(cause);
    }

    protected ChatBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
