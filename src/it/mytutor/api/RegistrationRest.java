package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("registration")
public class RegistrationRest {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getRegistration(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Registration</h1>";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String registration(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">RICEVERE JSON PER REGISTRAZIONE</h1>";
    }

}
