package it.mytutor.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//Path("auth")
public class LoginRest {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getAuth(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente di login</h1>";
    }

    @Path("{SID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String getAuthToken(@PathParam("SID") String sid){
        System.out.println("Accedo al sid");
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componenet di login con @PathParam({SID}): "+ sid +" </h1>";
    }

    @Path("{SID}/chats")
    public ChatsRest getChatsRest(@PathParam("SID") String sid){
        System.out.println("Accedo al chats");
        return new ChatsRest(sid);
    }
}
