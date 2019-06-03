package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageDeserializer extends StdDeserializer<Message> {
    public MessageDeserializer() {
        this(null);
    }

    public MessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Message message = new Message();
        message.setIdMessage(node.get("idMessage").asInt());

        message.setText(node.get("text").asText());

        message.setSendDate(Timestamp.valueOf(node.get("sendDate").asText()));

        Chat chat = new Chat();
        JsonNode chatNode = node.get("chat");
        chat.setIdChat(chatNode.get("idChat").asInt());
        chat.setName(chatNode.get("name").asText());
        message.setIdChat(chat);

        List<User> userList = new ArrayList<User>();
        ArrayNode users = (ArrayNode) node.get("users");
        Iterator<JsonNode> usersIterator = users.elements();
        while (usersIterator.hasNext()) {
            JsonNode userNode = usersIterator.next();
            User user = new User();
            user.setIdUser(userNode.get("idUser").asInt());
            user.setEmail(userNode.get("email").asText());
            user.setPassword(userNode.get("password").asText());
            user.setName(userNode.get("name").asText());
            user.setSurname(userNode.get("surname").asText());
            user.setBirtday(Date.valueOf(userNode.get("birtday").asText()));
            user.setLanguage(userNode.get("language").asBoolean());
            user.setImage(userNode.get("image").asText());
            userList.add(user);
        }
        message.setIdUser(userList);

        return message;
    }
}
