package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Creates;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import java.io.IOException;

public class CreatesSerializer extends StdSerializer<Creates> {

    public CreatesSerializer() { this(null); }

    public CreatesSerializer(Class<Creates> t) { super(t); }

    @Override
    public void serialize(Creates creates, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("idCreates", creates.getIdCreates());
        long createDate = creates.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = creates.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);

        jsonGenerator.writeFieldName("userListser");
        jsonGenerator.writeStartArray();

        Student student = new Student();
        Teacher teacher = new Teacher();

        jsonGenerator.writeFieldName("student");
        jsonGenerator.writeStartObject();

        if (creates.getUserListser().get(0) instanceof Student) {
            student = (Student) creates.getUserListser().get(0);
        } else if(creates.getUserListser().get(0) instanceof Teacher) {
            teacher = (Teacher) creates.getUserListser().get(0);
        }
        if (creates.getUserListser().get(1) instanceof Student) {
            student = (Student) creates.getUserListser().get(1);
        } else if(creates.getUserListser().get(1) instanceof Teacher) {
            teacher = (Teacher) creates.getUserListser().get(1);
        }

        jsonGenerator.writeNumberField("idStudent", student.getIdStudent());
        jsonGenerator.writeStringField("studyGrade", student.getStudyGrade());
        long createDateStudent = student.getCreateDateStudent().getTime();
        jsonGenerator.writeNumberField("createDateStudent", createDateStudent);
        long updateDateStudent = student.getUpdateDateStudent().getTime();
        jsonGenerator.writeNumberField("updateDateStudent", updateDateStudent);
        jsonGenerator.writeFieldName("user");
        jsonGenerator.writeStartObject();
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
        jsonGenerator.writeEndObject();

        jsonGenerator.writeFieldName("teacher");
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
        long bMillisTeacher = teacher.getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillisTeacher);
        jsonGenerator.writeBooleanField("language", teacher.getLanguage());
        jsonGenerator.writeStringField("image", teacher.getImage());
        long createDateMillis = teacher.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = teacher.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("chat");
        jsonGenerator.writeNumberField("idChat", creates.getChat().getIdChat());
        jsonGenerator.writeStringField("name", creates.getChat().getName());
        long createDateCreates = creates.getChat().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateCreates);
        long updateDateCreates = creates.getChat().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateCreates);
        jsonGenerator.writeEndObject();


        jsonGenerator.writeEndObject();

    }
}