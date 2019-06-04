package it.mytutor.api;

import it.mytutor.business.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth/chats")
public class ChatsRest {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getChats(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Chats</h1>";
    }

    @Path("{CID}")
    @GET
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getChatsByIDByQuery(@PathParam("CID") String cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        // vedo se devo fare la query per vedere se esiste il cid
        if(numero==null && sotto==null){
            return this.getChatsByID(cid);
        }else {
            return "<h1 style=\"" +
                    "color: red; " +
                    "margin: auto; " +
                    "width: fit-content; " +
                    "margin-top: 20%;\" " +
                    ">Componente Chats con @PathParam(\"CID\"): " + cid + ", @QueryParam(\"numero\"): " + numero + " (a default vale null), @QueryParam(\"sotto\"):  " + sotto + " (a default vale null)</h1>";
        }
    }

    // query per vedere se esiste il cid
    private String getChatsByID(String cid){
        return "true";
    }

    @Path("{CID}")
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String postChats(@PathParam("CID") String cid){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Ricevo la richiesta di creazione Chat</h1>";
    }


}
