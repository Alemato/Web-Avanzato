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
        jsonGenerator.writeNumberField("idChat", message.getChat().getIdChat());
        jsonGenerator.writeStringField("chatName", message.getChat().getName());
        long chatCreateDate = message.getChat().getCreateDate().getTime();
        jsonGenerator.writeNumberField("chatCreateDate", chatCreateDate);
        long chatUpdateDate = message.getChat().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("chatUpdateDate", chatUpdateDate);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("user");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idUser", message.getUser().getIdUser());
        jsonGenerator.writeStringField("email", message.getUser().getEmail());
        jsonGenerator.writeNumberField("roles", message.getUser().getRoles());
        jsonGenerator.writeStringField("name", message.getUser().getName());
        jsonGenerator.writeStringField("surname", message.getUser().getSurname());
        long bMillis = message.getUser().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", message.getUser().getLanguage());
        jsonGenerator.writeStringField("image", message.getUser().getImage());
        long createDateMillis = message.getUser().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = message.getUser().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
