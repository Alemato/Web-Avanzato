package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Path("auth/{SID}/chats")
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getChatsByIDByQuery(@PathParam("CID") String cid, @QueryParam("numero") Integer numero){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Chats con @PathParam(\"CID\"): "+ cid + " e @QueryParam(\"numero\"): "+ numero +" a default vale null </h1>";
    }

}
