package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class MessageDeserializer extends StdDeserializer<Message> {
    public MessageDeserializer() {
        this(null);
    }

    public MessageDeserializer(Class<?> vc) {
        super(vc);
    }

    //TODO implementare il deseralizatore per messaggi
    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Message message = new Message();
        message.setText(node.get("text").asText());

        message.setSendDate(new Timestamp(node.get("sendDate").asLong()));

        Chat chat = new Chat();
        JsonNode chatNode = node.get("chat");
        if (chatNode.get("idChat") != null) {
            chat.setIdChat(chatNode.get("idChat").asInt());
        }
        chat.setName(chatNode.get("name").asText());
        message.setChat(chat);

        User user = new User();
        JsonNode userNode = node.get("user");
        user.setIdUser(userNode.findPath("idUser").asInt());
        user.setEmail(userNode.findPath("email").asText());
        user.setRoles(userNode.findPath("roles").asInt());
        user.setName(userNode.findPath("name").asText());
        user.setSurname(userNode.findPath("surname").asText());
        Date bDate = new Date(userNode.get("birthday").asLong());
        user.setBirthday(bDate);
        user.setLanguage(Boolean.getBoolean(userNode.get("language").asText()));
        if(userNode.get("image").asText().equals("null") || userNode.get("image").asText().equals("")){
            user.setImage(null);
        } else user.setImage(userNode.get("image").asText());
        message.setUser(user);

        return message;
    }
}
