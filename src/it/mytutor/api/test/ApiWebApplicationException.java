package it.mytutor.api.test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ApiWebApplicationException extends WebApplicationException {
    public ApiWebApplicationException() {
        super(Response.serverError().build());
    }

    public ApiWebApplicationException(String message) {
        super(Response.serverError()
                .entity("{ \"status\" : \""+ Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + "\", \"message\" : \"" + message + "\" }")
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
    public ApiWebApplicationException(String message, int status){
        super(Response.status(status).entity("{ \"status\" : \"status\", \"message\" : \"" + message + "\" }").type(MediaType.APPLICATION_JSON).build());
    }
}
