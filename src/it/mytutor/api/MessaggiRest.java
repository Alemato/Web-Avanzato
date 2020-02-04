package it.mytutor.api;

import it.mytutor.business.exceptions.MessageBusinessException;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Message;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("chats/{CID}/messages")
public class MessaggiRest {

    private MessageInterface messageService = new MessageBusiness();

    /**
     * Rest per tutti i messaggi della chat
     *
     * @param cid id della chat ricevuto dal client
     * @return lista di messaggi
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getAllMessaggi(@PathParam("CID") Integer cid) {
        List<Message> messages = null;
        try {
            messages = new ArrayList<>(messageService.findAllMessageByChat(cid));
        } catch (MessageBusinessException e) {
            e.printStackTrace();
        }
        return Response.ok(messages).build();
    }

    /**
     * @param message Oggetto messaggio da creare
     * @param cid Path Param chat collegata al messaggio da creare
     * @return 201 CREATED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response createMessage(Message message, @PathParam("CID") Integer cid) {
        try {
            messageService.crateMessage(message);
        } catch (MessageBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     *  Rest per prendere i nuovi messaggi se ce ne sono
     * @param idChat id della chat collegata agli ultimi messaggi richiesti
     * @param idLastMessage ultimo id
     * @return  lista di nuovi messaggi o 304 NOT_MODIFIED
     */
    @Path("last")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response MessageAfterId(@PathParam("CID") Integer idChat, @QueryParam("id-last-message") Integer idLastMessage) {
        List<Message> messages;
        try {
            messages = messageService.getNewMessagesByIdLast(idChat, idLastMessage);
        } catch (MessageBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        if (!messages.isEmpty()) {
            return Response.ok(messages).build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).entity("nessun nuovo messaggio").build();
        }
    }




    @Path("count")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response CountMessage(@PathParam("CID") Integer idChat) {
        List<Message> messages;
        try {
            messages = messageService.findAllMessageByChat(idChat);
        } catch (MessageBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(messages.size()).build();
    }

/*    @Path("allchat")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getAllMessageOfAChat(@QueryParam("id-chat") Integer idChat) {
        List<Message> messages = new ArrayList<>();
        try {
            messages = messageService.findAllMessageByChat(idChat);
        } catch (MessageBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(messages).build();
    }*/



    /*@Path("new")
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
    }*/

/*    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getMessaggi(@PathParam("CID") Integer cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {

        Chat chat = new Chat();
        chat.setIdChat(cid);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        chat.setName("chat col prof");
        chat.setCreateDate(timestamp);
        chat.setUpdateDate(timestamp);
        List<Message> messages = new ArrayList<>(messageService.findAllMessageByChat(chat));
        List<Message> messages20 = new ArrayList<Message>();
        int index = 0;
        if (numero == null && sotto == null) {
            if (messages.size() > 20) {
                messages20.subList(0, 20);
            } else {
                messages20.addAll(messages);
            }
        } else {
            for (Message message : messages) {
                if (message.getIdMessage().equals(sotto)) {
                    index = messages.indexOf(message);
                }
            }
            if (messages.size() > index + 1 + numero) {
                messages20.subList(index + 1, index + 1 + numero);
            } else {
                messages20.subList(index + 1, messages.size());
            }
        }
        return Response.ok(messages20).build();
    }*/

}
