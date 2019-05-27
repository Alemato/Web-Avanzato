package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Path("auth/{SID}/prenotazioni-lezioni")
public class PrenotazioniLezioniRest {
    private String sid;

    public PrenotazioniLezioniRest(@PathParam("SID") String sid) {
        this.sid = sid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@QueryParam("filtro") String filtro){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni-Lezione con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }
}

