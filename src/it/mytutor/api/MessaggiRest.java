package it.mytutor.api;

import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path("chats/{CID}/messaggi")
@PermitAll
public class MessaggiRest {

    private MessageInterface messageService = new MessageBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getMessaggi(@PathParam("CID") Integer cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){

        Chat chat = new Chat();
        chat.setIdChat(cid);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chat.setName("chat col prof");
        chat.setCreateDate(timestamp);
        chat.setUpdateDate(timestamp);
        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
        List<Message> messages20 = new ArrayList<Message>();
        Integer index = 0;
        int count = 0;
        if (numero == null && sotto == null) {
            if (messages.size() > 20){
                messages20.subList(0, 20);
            } else{
                messages20.addAll(messages);
            }
        }else {
            for (Message message : messages) {
                if (message.getIdMessage().equals(sotto)) {
                    index = messages.indexOf(message);
                }
            }
            if (messages.size() > index + 1  + numero) {
                messages20.subList(index + 1 , index + 1  + numero);
            } else {
                messages20.subList(index + 1 , messages.size());
            }
        }
        return Response.ok(messages20).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response setMessaggi(Message message, @PathParam("CID") Integer cid){
        Chat chat = new Chat();
        chat.setIdChat(cid);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chat.setUpdateDate(timestamp);
        chat.setCreateDate(timestamp);
        chat.setName(message.getIdChat().getName());
        message.setIdChat(chat);
        message.setIdMessage(2);
        message.setUpdateDate(timestamp);
        message.setCreateDate(timestamp);
//        messageService.crateMessage(message);
        System.out.println(message.toString());
        return Response.ok(message).build();
    }

    @Path("{MID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getMessaggi(@PathParam("CID") String cid, @PathParam("MID") Integer mid){
        Message message = new Message();
        //TODO trigger db per nuovo singolo messaggio
        message = messageService.getMessageById(mid);
        return Response.ok(message).build();
    }

}
