package it.mytutor.api.test;

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

    @Path("/all")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getALL(){
        return "IS SECURE ALL";
    }

}
