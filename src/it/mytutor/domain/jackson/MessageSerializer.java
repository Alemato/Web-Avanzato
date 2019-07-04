package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<Message> {
    public MessageSerializer() {
        this(null);
    }

    public MessageSerializer(Class<Message> t) {
        super(t);
    }

    @Override
    public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idMessage", message.getIdMessage());
        jsonGenerator.writeStringField("text", message.getText());
        long sendDate = message.getSendDate().getTime();
        jsonGenerator.writeNumberField("sendDate", sendDate);
        long createDate = message.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = message.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeFieldName("chat");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idChat", message.getIdChat().getIdChat().toString());
        jsonGenerator.writeStringField("name", message.getIdChat().getName());
        long chatCreateDate = message.getIdChat().getCreateDate().getTime();
        jsonGenerator.writeNumberField("chatCreateDate", chatCreateDate);
        long chatUpdateDate = message.getIdChat().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("chatUpdateDate", chatUpdateDate);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("users");
        jsonGenerator.writeStartArray();
        for (User user : message.getIdUser()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("idUser", user.getIdUser());
            jsonGenerator.writeStringField("name", user.getName());
            jsonGenerator.writeStringField("surname", user.getSurname());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
