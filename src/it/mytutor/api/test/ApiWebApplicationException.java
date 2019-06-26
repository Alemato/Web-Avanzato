package it.mytutor.api.test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ApiWebApplicationException extends WebApplicationException {
    public ApiWebApplicationException() {
        super(Response.serverError().build());
    }

    public ApiWebApplicationException(String message) {
        super(Response.serverError()
                .entity(message)
                .type("text/plain")
                .build());
    }
}
