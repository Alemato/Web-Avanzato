package it.mytutor.api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/prenotazioni-lezioni")
public class PrenotazioniLezioniRest {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@QueryParam("filtro") String filtro){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni-Lezione con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }
}

