package it.mytutor.business.exceptions;


public class BookingBusinessException extends Exception {

    public BookingBusinessException() {
        super();
    }

    public BookingBusinessException(String message) {
        super(message);
    }

    public BookingBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingBusinessException(Throwable cause) {
        super(cause);
    }

    protected BookingBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
