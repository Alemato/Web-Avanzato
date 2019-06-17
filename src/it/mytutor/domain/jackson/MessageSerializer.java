package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

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

        jsonGenerator.writeStartObject("message");

        jsonGenerator.writeNumberField("idMessage",message.getIdMessage());
        jsonGenerator.writeStringField("text", message.getText());
        jsonGenerator.writeStringField("sendDate",message.getSendDate().toString());
        jsonGenerator.writeStringField("createDate",message.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate",message.getUpdateDate().toString());

        jsonGenerator.writeFieldName("chat");
        jsonGenerator.writeStartObject("chat");
        jsonGenerator.writeStringField("idChat", message.getIdChat().getIdChat().toString());
        jsonGenerator.writeStringField("name", message.getIdChat().getName());
        jsonGenerator.writeStringField("createDate", message.getIdChat().getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", message.getIdChat().getUpdateDate().toString());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeFieldName("users");
        jsonGenerator.writeStartArray();
        for (User user: message.getIdUser()){
            jsonGenerator.writeFieldName("user");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("iduser",user.getIdUser().toString());
            jsonGenerator.writeStringField("email",user.getEmail());
            jsonGenerator.writeStringField("password",user.getPassword());
            jsonGenerator.writeStringField("name",user.getName());
            jsonGenerator.writeStringField("surname",user.getSurname());
            jsonGenerator.writeStringField("birtday",user.getBirtday().toString());
            jsonGenerator.writeStringField("language",user.getLanguage().toString());
            jsonGenerator.writeStringField("image",user.getImage());
            jsonGenerator.writeStringField("createDate",user.getCreateDate().toString());
            jsonGenerator.writeStringField("updateDate",user.getUpdateDate().toString());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
