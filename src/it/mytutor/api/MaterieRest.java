package it.mytutor.api;

import it.mytutor.business.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/materie")
public class MaterieRest {

    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMaterie(@QueryParam("filtro") String filtro){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Materie con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String creaMaterie(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Crea Materie</h1>";
    }
}
