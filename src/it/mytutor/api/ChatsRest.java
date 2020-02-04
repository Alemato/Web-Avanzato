package it.mytutor.api;

import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


@Path("chats")
public class ChatsRest {
    @Context
    private SecurityContext securityContext;

    private ChatInterface chatService = new ChatBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * rest per ricevere la lista di tutte le chat che saranno salvate nello storage
     * Con un solo ULTIMO MESSAGGIO ricevuto o inviato.
     * @return lista di ultimi messaggi per ogni chat
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getAllChat(){
        String email = securityContext.getUserPrincipal().getName();
        List<Message> messages;
        try {
            messages = chatService.findAllChatByUser(email);
        }catch (DatabaseException e){
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(messages).build();
    }

    /**
     * rest per ricevere la lista di tutte le chat che saranno salvate nello storage
     * Con un solo l'ULTIMO MESSAGGIO ricevuto o inviato.
     * @return lista di ultimi messaggi per ogni chat
     */
    @Path("count")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getChatCount(@QueryParam("id-user2") String idUser2){
        String username = securityContext.getUserPrincipal().getName();
        List<Message> messageList;
        if (idUser2 != null && !idUser2.isEmpty() && !idUser2.equals(" ")) {
            try {
                if (chatService.getIfExistChat(username, Integer.parseInt(idUser2))){
                    return Response.ok(1).build();
                } else {
                    return Response.ok(0).build();
                }
            } catch (ChatBusinessException e) {
                e.printStackTrace();
            }
        }

        try {
            messageList = chatService.findAllChatByUser(username);
            return Response.ok(messageList.size()).build();
        }catch (DatabaseException e){
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
    }

    /**
     * Api Rest usata per crere una nuova chat tra l'utente stesso che richiede la
     * Rest e l'utente identificato dalli'id-addressee
     *
     * @param addressee id user del destinatario
     * @return Risposta custom con stato 201 Created
     */
    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response createChat(@QueryParam("id-addressee") Integer addressee) {
        User userCreate;

        String email = securityContext.getUserPrincipal().getName();
        try {
            userCreate = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            chatService.creationChat(userCreate, addressee);
        } catch (ChatBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

}
