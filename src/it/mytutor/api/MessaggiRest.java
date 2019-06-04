package it.mytutor.api;

import it.mytutor.business.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/chats/{CID}/messaggi")
public class MessaggiRest {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Messaggi</h1>";
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@PathParam("CID") String cid){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Creazione Messaggi</h1>";
    }


    @Path("{MID}")
    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@PathParam("CID") String cid, @PathParam("MID") String mid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Messaggi con @PathParam(\"MID\"): "+ mid +" @QueryParam(\"numero\"):" + numero + " (a default vale null), @QueryParam(\"sotto\"): " + sotto + " (a default vale null)</h1>";
    }

}
