package it.mytutor.business.exceptions;

public class SubjectBusinessException extends Exception {

    public SubjectBusinessException() {
        super();
    }

    public SubjectBusinessException(String message) {
        super(message);
    }

    public SubjectBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubjectBusinessException(Throwable cause) {
        super(cause);
    }

    protected SubjectBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
