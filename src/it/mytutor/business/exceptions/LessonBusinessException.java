package it.mytutor.business.exceptions;

public class LessonBusinessException extends Exception {

    public LessonBusinessException() {
        super();
    }

    public LessonBusinessException(String message) {
        super(message);
    }

    public LessonBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LessonBusinessException(Throwable cause) {
        super(cause);
    }

    protected LessonBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
