package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Message;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<Message> {
    public MessageSerializer(){
        this(null);
    }

    public MessageSerializer(Class<Message> t) {
        super(t);
    }

    @Override
    public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idMessage",message.getIdMessage());
        jsonGenerator.writeStringField("text", message.getText());
        jsonGenerator.writeStringField("sendDate",message.getSendDate().toString());
        jsonGenerator.writeStringField("createDate",message.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate",message.getUpdateDate().toString());
        jsonGenerator.writeNumberField("idChat",message.getIdChat());
        jsonGenerator.writeNumberField("idUser",message.getIdUser());
        jsonGenerator.writeEndObject();
    }
}
