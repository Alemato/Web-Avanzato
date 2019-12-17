package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.CreatesBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.business.services.CreatesInterface;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Creates;
import it.mytutor.domain.Message;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("chats")
public class ChatsRest {

    private ChatInterface chatService = new ChatBusiness();
    private MessageInterface messageService = new MessageBusiness();
    private CreatesInterface createsService = new CreatesBusiness();

    /**
     * rest per ricevere la lista di tutte le chat che saranno salvate nello storage
     * Con un solo l'ULTIMO MESSAGGIO ricevuto o inviato.
     * @param requestContext
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getAllChat(ContainerRequestContext requestContext){
        String username = requestContext.getSecurityContext().getUserPrincipal().getName();
        List<Message> messageList;
        try {
            messageList = chatService.findAllChatByUser(username);
            return Response.ok(messageList).build();
        }catch (DatabaseException e){
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
    }

    /**
     * Query rest chiamata per ricevere la lista di tutte le chat che
     * saranno salvate nello storage
     * gli passiamo 0 per tutte le chat sue
     * gli passiamo id del ultima per vedere le sole ultime chat
     * @return lista di primi messaggi di ogni chat rappresentante la lista delle chat
     */
    @Path("byid")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getChatsByIDByQuery(ContainerRequestContext requestContext, @QueryParam("idUltimaChat") Integer idUltimaChat) {
        String username = requestContext.getSecurityContext().getUserPrincipal().getName();
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
    }

    /**
     * @param creates oggetto preso dal json
     * @return Response, 200 se è andato tutto bene
     */
    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response postChats(Creates creates) {
        System.out.println(creates);
//        chatService.creationChat(creates.getChat());
        createsService.createCreates(creates);
        return Response.ok().build();
    }


}
