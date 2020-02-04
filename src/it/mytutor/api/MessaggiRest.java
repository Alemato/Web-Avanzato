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
     * Rest che ritorna tutti i messaggi della chat identificata dall'id CID
     *
     * @param cid id della chat
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
     * Rest che si occupa della creazione di un messaggio
     *
     * @param message Oggetto messaggio da creare
     * @param cid Path Param chat collegata al messaggio da creare
     * @return Risposta custom con stato 201 Created
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
     *  Rest usata per prendere i nuovi messaggi, se ce ne sono
     *
     * @param idChat id della chat collegata agli ultimi messaggi richiesti
     * @param idLastMessage ultimo id
     * @return  lista di nuovi messaggi
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

        return Response.ok(messages).build();
    }


    /**
     * Rest che si occupa di tornare in numero dei messaggi presenti nella chat identificata dall'id CID
     *
     * @param idChat id della chat
     * @return numero di messaggi della chat
     */
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

}
