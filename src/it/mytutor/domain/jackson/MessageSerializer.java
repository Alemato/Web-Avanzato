package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.*;

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


        jsonGenerator.writeFieldName("userListser");
        jsonGenerator.writeStartArray();

        Student student = new Student();
        Teacher teacher = new Teacher();
        Chat chat = message.getChat();
        if (chat.getUserListser().get(0) instanceof Student) {
            student = (Student) chat.getUserListser().get(0);
        } else if(chat.getUserListser().get(0) instanceof Teacher) {
            teacher = (Teacher) chat.getUserListser().get(0);
        }
        if (chat.getUserListser().get(1) instanceof Student) {
            student = (Student) chat.getUserListser().get(1);
        } else if(chat.getUserListser().get(1) instanceof Teacher) {
            teacher = (Teacher) chat.getUserListser().get(1);
        }

//        jsonGenerator.writeFieldName("student");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idStudent", student.getIdStudent());
        jsonGenerator.writeStringField("studyGrade", student.getStudyGrade());
        long createDateStudent = student.getCreateDateStudent().getTime();
        jsonGenerator.writeNumberField("createDate", createDateStudent);
        long updateDateStudent = student.getUpdateDateStudent().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateStudent);

        jsonGenerator.writeNumberField("idUser", student.getIdUser());
        jsonGenerator.writeStringField("email", student.getEmail());
        jsonGenerator.writeNumberField("roles", student.getRoles());
        jsonGenerator.writeStringField("name", student.getName());
        jsonGenerator.writeStringField("surname", student.getSurname());
        long bMillis = student.getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", student.getLanguage());
        jsonGenerator.writeStringField("image", student.getImage());
        long createDateUser = student.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateUser);
        long updateDateUser = student.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateUser);
        jsonGenerator.writeEndObject();

        //        jsonGenerator.writeFieldName("teacher");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idTeacher", teacher.getIdTeacher());
        jsonGenerator.writeNumberField("postCode", teacher.getPostCode());
        jsonGenerator.writeStringField("city", teacher.getCity());
        jsonGenerator.writeStringField("region", teacher.getRegion());
        jsonGenerator.writeStringField("street", teacher.getStreet());
        jsonGenerator.writeStringField("streetNumber", teacher.getStreetNumber());
        jsonGenerator.writeStringField("byography", teacher.getByography());
        long crateDateTeacherMillis = teacher.getCrateDateTeacher().getTime();
        jsonGenerator.writeNumberField("crateDate", crateDateTeacherMillis);
        long updateDateTeacherMillis = teacher.getUpdateDateTeacher().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateTeacherMillis);

        jsonGenerator.writeNumberField("idUser", teacher.getIdUser());
        jsonGenerator.writeStringField("email", teacher.getEmail());
        jsonGenerator.writeNumberField("roles", teacher.getRoles());
        jsonGenerator.writeStringField("name", teacher.getName());
        jsonGenerator.writeStringField("surname", teacher.getSurname());
        long bMillisTeacher = teacher.getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillisTeacher);
        jsonGenerator.writeBooleanField("language", teacher.getLanguage());
        jsonGenerator.writeStringField("image", teacher.getImage());
        long createDateMillis = teacher.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = teacher.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndArray();


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
        long bMillisu = message.getUser().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillisu);
        jsonGenerator.writeBooleanField("language", message.getUser().getLanguage());
        jsonGenerator.writeStringField("image", message.getUser().getImage());
        long createDateMillisu = message.getUser().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillisu);
        long updateDateMillisu = message.getUser().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillisu);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
