package it.mytutor.business.exceptions;


public class PlanningBusinessException extends Exception {

    public PlanningBusinessException() {
        super();
    }

    public PlanningBusinessException(String message) {
        super(message);
    }

    public PlanningBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanningBusinessException(Throwable cause) {
        super(cause);
    }

    protected PlanningBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
