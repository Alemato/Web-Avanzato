package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/{SID}/materie")
public class MaterieRest {
    private String sid;

    public MaterieRest(@PathParam("SID") String sid){
        this.sid=sid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMaterie(@QueryParam("filtro") String filtro){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }
}
