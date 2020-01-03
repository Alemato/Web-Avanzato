package it.mytutor.api;

import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.ChatInterface;
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
public class MessaggiRest {

    private MessageInterface messageService = new MessageBusiness();
    private ChatInterface chatService = new ChatBusiness();

//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @PermitAll
//    public Response getMessaggi(@PathParam("CID") Integer cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {
//
//        Chat chat = new Chat();
//        chat.setIdChat(cid);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        chat.setName("chat col prof");
//        chat.setCreateDate(timestamp);
//        chat.setUpdateDate(timestamp);
//        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
//        List<Message> messages20 = new ArrayList<Message>();
//        int index = 0;
//        if (numero == null && sotto == null) {
//            if (messages.size() > 20) {
//                messages20.subList(0, 20);
//            } else {
//                messages20.addAll(messages);
//            }
//        } else {
//            for (Message message : messages) {
//                if (message.getIdMessage().equals(sotto)) {
//                    index = messages.indexOf(message);
//                }
//            }
//            if (messages.size() > index + 1 + numero) {
//                messages20.subList(index + 1, index + 1 + numero);
//            } else {
//                messages20.subList(index + 1, messages.size());
//            }
//        }
//        return Response.ok(messages20).build();
//    }


    /**
     * Query rest che chiamata la prima volta che si apre una chat
     * e nel relativo storage del client non vi è nulla
     * @param cid id della chat ricevuto dal client
     * @return lista di messaggi
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getAllMessaggi(@PathParam("CID") Integer cid) {
        Chat chat = chatService.findChatByID(cid);
        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
        return Response.ok(messages).build();
    }

    /**
     * Query rest chiamata per vedere se ci sono nuovi messaggi
     * ed in tal caso riceverli
     * @param cid id della chat ricevuto dal client
     * @param idLastMessage id dell'ultimo messaggio che il client h nello storage
     * @return lista di messaggi oppure 304 per nessun mnuovo messaggio
     */
    @Path("new")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getNewMessaggi(@PathParam("CID") Integer cid, @QueryParam("idlastmessage") Integer idLastMessage) {
        List<Message> newMessages = new ArrayList<>(messageService.getNewMessagesByIdLast(idLastMessage));
        if (!(newMessages.isEmpty())) {
            return Response.ok(newMessages).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).entity("nessun nuovo messaggio").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response setMessaggi(Message message, @PathParam("CID") Integer cid) {
        Chat chat = new Chat();
        chat.setIdChat(cid);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chat.setUpdateDate(timestamp);
        chat.setCreateDate(timestamp);
        chat.setName(message.getChat().getName());
        message.setChat(chat);
        message.setIdMessage(2);
        message.setUpdateDate(timestamp);
        message.setCreateDate(timestamp);
//        messageService.crateMessage(message);
        System.out.println(message.toString());
        return Response.ok(message).build();
    }
}
