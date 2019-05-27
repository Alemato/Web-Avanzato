package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Path("auth/{SID}/chats/{CID}/messaggi")
public class MessaggiRest {
    private String sid;
    private String cid;

    public MessaggiRest(@PathParam("SID") String sid, @PathParam("CID") String cid) {
        this.sid = sid;
        this.cid = cid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        System.out.println("il sid è: "+sid);
        System.out.println("il cid è: "+cid);
        // vedp se ha un SID e CID valido
        ChatsRest chatsRest = new ChatsRest(sid);
        System.out.println(chatsRest.getChatsByIDByQuery(cid, null, null));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Messaggi con @QueryParam(\"numero\"):" + numero + " (a default vale null), @QueryParam(\"sotto\"): " + sotto + " (a default vale null)</h1>";
    }

    @Path("{MID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getMessaggi(@PathParam("MID") String mid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        System.out.println("il sid è: "+sid);
        System.out.println("il cid è: "+cid);
        // vedp se ha un SID e CID valido
        ChatsRest chatsRest = new ChatsRest(sid);
        System.out.println(chatsRest.getChatsByIDByQuery(cid, null, null));
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Messaggi con @PathParam(\"MID\"): "+ mid +" @QueryParam(\"numero\"):" + numero + " (a default vale null), @QueryParam(\"sotto\"): " + sotto + " (a default vale null)</h1>";
    }

}
