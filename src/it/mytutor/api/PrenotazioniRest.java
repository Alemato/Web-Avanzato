package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/{SID}/lezioni/{LID}/prenotazioni")
public class PrenotazioniRest {
    private String sid;
    private String lid;

    public PrenotazioniRest(@PathParam("SID") String sid, @PathParam("LID") String lid){
        this.sid = sid;
        this.lid = lid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getPrenotazioni(@QueryParam("filtro") String filtro){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }

    @Path("{PID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getPrenotazioniByID(@PathParam("PID") String pid){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni By ID con @PathParam(\"PID\"):" + pid + " (a default vale null)</h1>";
    }
}
