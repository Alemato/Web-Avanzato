package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/lezioni/{LID}/prenotazioni")
public class PrenotazioniRest {
    private String lid;

    public PrenotazioniRest(@PathParam("LID") String lid){
        this.lid = lid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getPrenotazioni(@QueryParam("filtro") String filtro){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String creaPrenotazione(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Crea Prenotazione</h1>";
    }

    @Path("{PID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String rispostaTheacher(@PathParam("PID") String pid){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Risposta a prenotazione con @PathParam(\"PID\"):" + pid + " (a default vale null)</h1>";
    }
}
