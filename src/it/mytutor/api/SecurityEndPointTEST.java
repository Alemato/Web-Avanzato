package it.mytutor.api;

import it.mytutor.business.security.Role;
import it.mytutor.business.security.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sec")
public class SecurityEndPointTEST {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getNot(){
        return "NOT SECURE";
    }

    @Path("/admin")
    @GET
    @Secured
    @Produces(MediaType.TEXT_HTML)
    public String getADMIN(){
        return "IS SECURE AND ADMIN";
    }

}
