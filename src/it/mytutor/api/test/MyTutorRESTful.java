package it.mytutor.api.test;

import it.mytutor.api.LoginRest;
import it.mytutor.api.RegistrationRest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("prova-student")
public class MyTutorRESTful {
    @GET
    @Consumes({MediaType.APPLICATION_JSON,})
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed({"STUDENT"})
    public String getRoot(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Root del app dove chiamiamo i componenti</h1>";
    }

    @Path("auth")
    public LoginRest getLoginRest(){
        return new LoginRest();
    }

    @Path("registration")
    public RegistrationRest getRegistrationRest(){
        return new RegistrationRest();
    }
}
