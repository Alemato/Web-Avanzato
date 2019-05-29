package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/{SID}/profili")
public class ProfiliRest {
    private  String sid;

    public ProfiliRest(@PathParam("SID") String sid){
        this.sid=sid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getProfili(@QueryParam("filtro") String filtro){
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

    @Path("{UID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getProfiliByID(@PathParam("UID") String uid){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @PathParam(\"UID\"):" + uid + " (a default vale null)</h1>";
    }
}
