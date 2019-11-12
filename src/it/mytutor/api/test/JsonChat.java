package it.mytutor.api.test;

import it.mytutor.business.impl.ChatBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("json-chat")
public class JsonChat {

    private ChatInterface service = new ChatBusiness();
    private MessageInterface messageService = new MessageBusiness();

    @Path("list")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public String getAllChat() {
        User user = new User();
        user.setIdUser(1);
        user.setEmail("mario@gmail.it");
        user.setPassword("123456789");
        user.setName("pippo");
        user.setSurname("franco");
        List<Chat> chats = service.findAllChatByUser(user);
        List<Message> messages = new ArrayList<>();
        List<Message> messagesChats = new ArrayList<>();
        List<Message> messages1 = new ArrayList<>();
        messages = messageService.findAllMessageByUser(user);
        for (Chat chat : chats) {

            for (Message message : messages) {
                if (message.getIdChat().getIdChat().equals(chat.getIdChat()))
                    messages1.add(message);
            }
            messagesChats.add(messages1.get(0));
        }
        List<String> chatName = new ArrayList<>();
        StringBuilder chatString = new StringBuilder();
        for (Message message : messagesChats) {

            chatName.add("<h1>" + message.getIdChat().getName() + "</h1><br>");
        }
        for (String string : chatName) {
            chatString.append(string);
        }
        return chatString.toString();
    }

    @Path("singola")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Chat getChatById(@QueryParam("id") int id) {
        return service.findChatByID(id);
    }

    @Path("molte")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @PermitAll
    public Response getChatMolte() {
        User user = new User();

        List<Chat> chats = service.findAllChatByUser(user);

        return Response.ok(chats).build();
    }

}
