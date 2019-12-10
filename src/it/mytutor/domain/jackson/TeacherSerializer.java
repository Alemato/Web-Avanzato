package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Teacher;

import java.io.IOException;

public class TeacherSerializer extends StdSerializer<Teacher> {
    public TeacherSerializer() { this(null); }

    public TeacherSerializer(Class<Teacher> t) { super(t); }

    @Override
    public void serialize(Teacher teacher, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idTeacher", teacher.getIdTeacher());
        jsonGenerator.writeNumberField("postCode", teacher.getPostCode());
        jsonGenerator.writeStringField("City", teacher.getCity());
        jsonGenerator.writeStringField("region", teacher.getRegion());
        jsonGenerator.writeStringField("street", teacher.getStreet());
        jsonGenerator.writeStringField("streetNumber", teacher.getStreetNumber());
        jsonGenerator.writeStringField("byography", teacher.getByography());
        long crateDateTeacherMillis = teacher.getCrateDateTeacher().getTime();
        jsonGenerator.writeNumberField("crateDateTeacher", crateDateTeacherMillis);
        long updateDateTeacherMillis = teacher.getUpdateDateTeacher().getTime();
        jsonGenerator.writeNumberField("updateDateTeacher",updateDateTeacherMillis);
        jsonGenerator.writeFieldName("user");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idUser", teacher.getIdUser());
        jsonGenerator.writeStringField("email", teacher.getEmail());
        jsonGenerator.writeNumberField("roles", teacher.getRoles());
        jsonGenerator.writeStringField("name", teacher.getName());
        jsonGenerator.writeStringField("surname", teacher.getSurname());
        long bMillis = teacher.getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", teacher.getLanguage());
        jsonGenerator.writeStringField("image", teacher.getImage());
        long createDateMillis = teacher.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = teacher.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
