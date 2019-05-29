package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Path("auth/{SID}/lezioni")
public class LezioniRest {
    private String sid;

    public LezioniRest(@PathParam("SID") String sid) {
        this.sid = sid;
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getLezioni(@QueryParam("filtro") String filtro, @QueryParam("numero") String numero, @QueryParam("sotto") String sotto){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @QueryParam(\"filtro\"):" + filtro +", @QueryParam(\"numero\"):" + numero  +" (a default vale null) e @QueryParam(\"sotto\"):" + sotto + " (a default vale null)</h1>";
    }

    @Path("{LID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getLezioniByID(@PathParam("LID") Integer lid,@QueryParam("numero") String numero, @QueryParam("sotto") String sotto){
        System.out.println("il sid è: "+sid);
        // vedp se ha un SID valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Lezioni con @PathParam(\"LID\"): "+ lid +", @QueryParam(\"numero\"):" + numero  +" (a default vale null) e @QueryParam(\"sotto\"):" + sotto + " (a default vale null)</h1>";
    }




}
