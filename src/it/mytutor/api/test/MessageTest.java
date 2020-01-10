package it.mytutor.api.test;

import it.mytutor.business.impl.MessageBusiness;
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
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("messaggi")
public class MessageTest {

    private MessageInterface service = new MessageBusiness();

/*    @Path("all")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @PermitAll
    public List<Message> getMessageByUser() {

        Chat chat = new Chat();
        return service.findAllMessageByChat(0);
    }*/

    @Path("new")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String postNewMessage(Message message, @Context UriInfo uriInfo) {
        System.out.println(message);
        return "<h1>" + message.getText() + "</h1>";
    }
}
