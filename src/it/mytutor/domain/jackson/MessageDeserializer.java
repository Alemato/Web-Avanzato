package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Message;

import java.io.IOException;
import java.sql.Timestamp;

public class MessageDeserializer extends StdDeserializer<Message> {
    public MessageDeserializer(){
        this(null);
    }

    public MessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Message message = new Message();
//        message.setIdMessage(node.get("idMessage").asInt());
//        message.setText(node.get("text").asText());
//        message.setSendDate(Timestamp.valueOf(node.get("sendDate").asText()));
//        message.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));
//        message.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));
//        message.setIdChat(node.get("idChat").asInt());
//        message.setIdUser(node.get("idUser").asInt());
        return message;
    }
}
