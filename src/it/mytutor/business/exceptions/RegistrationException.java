package it.mytutor.business.exceptions;

public class RegistrationException extends Exception  {

    public RegistrationException() {
        super();
    }

    public RegistrationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }
}
