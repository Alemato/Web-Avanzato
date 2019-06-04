package it.mytutor.api;

import it.mytutor.business.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/lezioni")
public class LezioniRest {

    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getLezioni(@QueryParam("filtro") String filtro, @QueryParam("numero") String numero, @QueryParam("sotto") String sotto){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @QueryParam(\"filtro\"):" + filtro +", @QueryParam(\"numero\"):" + numero  +" (a default vale null) e @QueryParam(\"sotto\"):" + sotto + " (a default vale null)</h1>";
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String creaLezione(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Crea Lezioni</h1>";

    }


    @Path("{LID}")
    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getLezioniByID(@PathParam("LID") Integer lid,@QueryParam("numero") String numero, @QueryParam("sotto") String sotto){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @PathParam(\"LID\"): "+ lid +", @QueryParam(\"numero\"):" + numero  +" (a default vale null) e @QueryParam(\"sotto\"):" + sotto + " (a default vale null)</h1>";
    }

    @Path("{LID}")
    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String modificaLezione(@PathParam("LID") Integer lid){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @PathParam(\"LID\"): "+ lid +" (a default vale null)</h1>";
    }

}
