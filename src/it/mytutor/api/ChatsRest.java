package it.mytutor.api;

import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.CreatesBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.business.services.CreatesInterface;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Creates;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Path("auth/chats")    // da capire se serve CID
public class ChatsRest {

    private ChatInterface chatService = new ChatBusiness();
    private MessageInterface messageService = new MessageBusiness();
    private CreatesInterface createsService = new CreatesBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getChatsByIDByQuery(@QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {
        //TODO Prendere user da SID
        User user = new User();
        List<Chat> chats = new ArrayList<>(chatService.findAllChatByUser(user));
        System.out.println("chats");
        System.out.println(chats);
        List<Chat> chats20 = new ArrayList<Chat>();
        List<Message> messagesFin = new ArrayList<Message>();
        Integer index = 0;

        if (numero == null && sotto == null) {
            if (chats.size() > 20) {
                chats20 = chats.subList(0, 20);
            } else {
                chats20.addAll(chats);
            }
        } else {
            for (Chat chat : chats) {
                if (chat.getIdChat().equals(sotto)) {
                    index = chats.indexOf(chat);
                }
            }
            if (chats.size() > index + 1 + numero) {
                chats20 = chats.subList(index + 1, index + 1 + numero);
            } else {
                chats20.subList(index + 1, chats.size());
            }
        }
        System.out.println("chats20");
        System.out.println(chats20);
        for (Chat chat : chats20) {
            List<Message> messagesProv = new ArrayList<Message>(messageService.findAllMessageByChat(chat));
            System.out.println("messagesProv");
            System.out.println(messagesProv);
            Message lastMessage = Collections.max(messagesProv, Comparator.comparing(Message::getSendDate));
            messagesFin.add(lastMessage);
        }
        System.out.println("messagesFin");
        System.out.println(messagesFin);

        return Response.ok(messagesFin).build();
    }

    // query per vedere se esiste il cid
    private Response getChatsByID(String cid) {
        //TODO da capire se e cosa fa
        return Response.ok().build();
    }

    @Path("{CID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response postChats(Creates creates, @PathParam("CID") String cid) {
        chatService.creationChat(creates.getChat());
        createsService.createCreates(creates);
        return Response.ok().build();
    }


}
