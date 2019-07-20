package it.mytutor.api;

import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("auth/chats/{CID}/messaggi")
public class MessaggiRest {

    private MessageInterface messageService = new MessageBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessaggi(@PathParam("CID") Integer cid){
        Chat chat = new Chat();
        chat.setIdChat(cid);
        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
        List<Message> messages20 = new ArrayList<Message>();
        if (messages.size() > 20){
            messages20.subList(0, 20);
        } else{
            messages20.addAll(messages);
        }
        return Response.ok(messages20).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessaggi(@PathParam("CID") Integer cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        Chat chat = new Chat();
        chat.setIdChat(cid);
        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
        Integer index = 0;
        for (Message message: messages){
            if (message.getIdMessage().equals(sotto)){
                index = messages.indexOf(message);
            }
        }
        List<Message> messages20 = new ArrayList<Message>();
        if (messages.size() > index+numero){
            messages20.subList(index, index + numero);
        } else{
            messages20.subList(index,messages.size());
        }
        return Response.ok(messages20).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setMessaggi(Message message, @PathParam("CID") Integer cid){
//        Chat chat = new Chat();
//        chat.setIdChat(cid);
//        message.setIdChat(chat);
        messageService.crateMessage(message);
        return Response.ok().build();
    }

    @Path("{MID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessaggi(@PathParam("CID") String cid, @PathParam("MID") Integer mid){
        Message message = new Message();
        message = messageService.getMessageById(mid);
        return Response.ok(message).build();
    }

}
