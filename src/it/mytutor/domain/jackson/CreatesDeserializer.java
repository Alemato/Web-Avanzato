package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Creates;
import it.mytutor.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreatesDeserializer extends StdDeserializer<Creates> {
    public CreatesDeserializer() {
        this(null);
    }

    public CreatesDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Creates deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Creates creates = new Creates();
        creates.setName(node.get("name").asText());

        List<User> userList = new ArrayList<User>();
        ArrayNode users = (ArrayNode) node.get("users");
        Iterator<JsonNode> usersIterator = users.elements();
        while (usersIterator.hasNext()) {
            JsonNode userNode = usersIterator.next();
            User user = new User();
            user.setIdUser(userNode.findPath("idUser").asInt());
            userList.add(user);
        }
        creates.setUserListser(userList);

        Chat chat = new Chat();
        chat.setName(node.findPath("chat").findPath("name").asText());
        creates.setChat(chat);

        return creates;
    }
}
