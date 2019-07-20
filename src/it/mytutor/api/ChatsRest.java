package it.mytutor.api;

import it.mytutor.business.impl.ChatBusiness;
//import it.mytutor.business.impl.CreatesBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.ChatInterface;
//import it.mytutor.business.services.CreatesInterface;
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


@Path("auth/chats")
public class ChatsRest {

    private ChatInterface chatService = new ChatBusiness();
    private MessageInterface messageService = new MessageBusiness();
    //private CreatesInterface createsService = new CreatesBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String getChats(){
//         METODO 1
//
//        List Chat chats = findAllChatByUser(user(dal SID))
//        List Message messages = findAllMessageByUser(user(dal SID))
//        for(Chat chat: chats){
//
//         }

//        METODO 2
/*        //TODO Prendere user da SID
        User user = new User();
        List<Chat> chats = chatService.findAllChatByUser(user);
        List<Chat> chats20 = new ArrayList<Chat>();
        if (chats.size() > 20){
            chats20 = chats.subList(0, 20);
        } else{
            chats20.addAll(chats);
        }
        List<Message> messagesFin = new ArrayList<Message>();
        for (Chat chat: chats20){
            List<Message> messagesProv = new ArrayList<Message>(messageService.findAllMessageByChat(chat));
            Message lastMessage = Collections.max(messagesProv, Comparator.comparing(Message::getSendDate));
            messagesFin.add(lastMessage);
        }
        return  Response.ok(messagesFin).build();*/
    return "get";
    }


    @Path("{CID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getChatsByIDByQuery(@PathParam("CID") String cid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto){
        // vedo se devo fare la query per vedere se esiste il cid
        /*if(numero==null && sotto==null){
            return this.getChatsByID(cid);
        }else {

            //TODO Prendere user da SID
            User user = new User();
            List<Chat> chats = chatService.findAllChatByUser(user);
            Integer index = 0;
            for (Chat chat: chats){
                if (chat.getIdChat().equals(sotto)) {
                    index = chats.indexOf(chat);
                }
            }
            List<Chat> chats20 = new ArrayList<Chat>();
            List<Message> messagesFin = new ArrayList<Message>();
            if (chats.size() > index+numero){
                chats20 = chats.subList(index, index + numero);
            } else{
                chats20.subList(index, chats.size());
            }
            for (Chat chat: chats){
                List<Message> messagesProv = new ArrayList<Message>(messageService.findAllMessageByChat(chat));
                Message lastMessage = Collections.max(messagesProv, Comparator.comparing(Message::getSendDate));
                messagesFin.add(lastMessage);
            }
            return  Response.ok(messagesFin).build();
        }*/
        return  Response.ok().build();
    }
    /*// query per vedere se esiste il cid
    private Response getChatsByID(String cid){
        //TODO da capire se e cosa fa
        return Response.ok().build();
    }*/

    @Path("{CID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response postChats(Creates creates, @PathParam("CID") String cid){
        //chatService.creationChat(creates.getChat());
        //createsService.createCreates(creates);
     return Response.ok().build();
    }


}
