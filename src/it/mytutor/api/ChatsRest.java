package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.CreatesBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.business.services.CreatesInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Creates;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;


@Path("chats")
public class ChatsRest {
    @Context
    private SecurityContext securityContext;

    private ChatInterface chatService = new ChatBusiness();
    private CreatesInterface createsService = new CreatesBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * rest per ricevere la lista di tutte le chat che saranno salvate nello storage
     * Con un solo l'ULTIMO MESSAGGIO ricevuto o inviato.
     * @return lista di ultimi messaggi per ogni chat
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getAllChat(){
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        List<Object> list = new ArrayList<>();
        List<Creates> creates;
        List<Message> messages;
        try {
            messages = chatService.findAllChatByUser(email);
        }catch (DatabaseException e){
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }

        try {
            creates = createsService.getCreatesByIdUser(user.getIdUser());
        } catch (ChatBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }

        list.add(messages);
        list.add(creates);

        return Response.ok(list).build();
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
    public Response getChatCount(@QueryParam("idUser2") String idUser2){
        String username = securityContext.getUserPrincipal().getName();
        List<Message> messageList;
        if (idUser2 != null && !idUser2.isEmpty() && !idUser2.equals(" ")) {
            try {
                if (createsService.getIfExistCreates(username, Integer.parseInt(idUser2))){
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

/*    *//**
     * Query rest chiamata per ricevere la lista di tutte le chat che
     * saranno salvate nello storage
     * gli passiamo 0 per tutte le chat sue
     * gli passiamo id del ultima per vedere le sole ultime chat
     * @return lista di primi messaggi di ogni chat rappresentante la lista delle chat
     *//*
    @Path("byid")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getChatsByIDByQuery(@QueryParam("idUltimaChat") Integer idUltimaChat) {
        String username = securityContext.getUserPrincipal().getName();
        List<Message> messageList;
        try {
            if (idUltimaChat == 0){
                messageList = chatService.findAllChatByUserByQuery(username);
                return Response.ok(messageList).build();
            } else {
                System.out.println("altro");


                return Response.ok().build();
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
    }*/

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response createChat(@QueryParam("id-addressee") Integer addressee, @QueryParam("chat-name") String chatName) {
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        if (user.getRoles().equals(1)) {
            try {
                createsService.createCreates(addressee, chatName, user.getIdUser());
            } catch (ChatBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else {
            try {
                createsService.createCreates(user.getIdUser(), chatName, addressee);
            } catch (ChatBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok().build();
    }

/*    @Path("creates")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getCreates() {

        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        try {
            creates = createsService.getCreatesbyId(user.getIdUser())

        }catch (DatabaseException e){
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(creates).build();
    }*/



}
