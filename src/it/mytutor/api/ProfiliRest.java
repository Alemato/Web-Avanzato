package it.mytutor.api;

import it.mytutor.business.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/profili")
public class ProfiliRest {

    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getProfili(@QueryParam("filtro") String filtro){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }

    @Path("{UID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getProfiliByID(@PathParam("UID") String uid){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @PathParam(\"UID\"):" + uid + " (a default vale null)</h1>";
    }
}
