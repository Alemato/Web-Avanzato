package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

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
        
        jsonGenerator.writeFieldName("userListser");
        jsonGenerator.writeStartArray();
        
        Student student = new Student();
        Teacher teacher = new Teacher();

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
        
        long createDate = chat.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = chat.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeEndObject();
    }
}
