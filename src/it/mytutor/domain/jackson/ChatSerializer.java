package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Chat;

import java.io.IOException;

public class ChatSerializer extends StdSerializer<Chat> {

    public ChatSerializer() {
        this(null);
    }

    public ChatSerializer(Class<Chat> t) {
        super(t);
    }

    @Override
    public void serialize(Chat chat, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idChat", chat.getIdChat());
        jsonGenerator.writeStringField("name", chat.getName());
        long createDate = chat.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = chat.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeEndObject();
    }
}
