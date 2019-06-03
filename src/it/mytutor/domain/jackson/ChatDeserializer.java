package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Chat;

import java.io.IOException;
import java.sql.Timestamp;

public class ChatDeserializer extends StdDeserializer<Chat> {

    public ChatDeserializer(){
        this(null);
    }

    public ChatDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Chat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Chat chat= new Chat();
        chat.setIdChat(node.get("idChat").asInt());

        chat.setName(node.get("name").asText());

//        chat.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));
//        chat.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));
        return chat;
    }
}
