package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Path("auth/{SID}/chats")
public class ChatsRest {

    private String sid;

    public ChatsRest(@PathParam("SID") String sid) {
        this.sid = sid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getChats(){
        System.out.println(sid);
        // vedo se ha un AUTH token valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Chats</h1>";
    }

    @Path("{CID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getChatsByIDByQuery(@PathParam("CID") String cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        // vedo se ha un AUTH token valido
        LoginRest loginRest = new LoginRest();
        System.out.println(loginRest.getAuthToken(sid));
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

    @Path("{CID}/messaggi")
    public MessaggiRest geMessaggi(@PathParam("CID") String cid){
        return new MessaggiRest(sid, cid);
    }



}
