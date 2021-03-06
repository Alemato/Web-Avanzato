package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Student;

import java.io.IOException;

public class StudentSerializer extends StdSerializer<Student> {
    public StudentSerializer(){
        this(null);
    }

    public StudentSerializer(Class<Student> t) {
        super(t);
    }

    @Override
    public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idStudent", student.getIdStudent());
        jsonGenerator.writeStringField("studyGrade", student.getStudyGrade());
        long createDateStudent = student.getCreateDateStudent().getTime();
        jsonGenerator.writeNumberField("createDateStudent", createDateStudent);
        long updateDateStudent = student.getUpdateDateStudent().getTime();
        jsonGenerator.writeNumberField("updateDateStudent", updateDateStudent);

        jsonGenerator.writeNumberField("idUser", student.getIdUser());
        jsonGenerator.writeStringField("email", student.getEmail());
        jsonGenerator.writeNumberField("roles", student.getRoles());
        jsonGenerator.writeStringField("name", student.getName());
        jsonGenerator.writeStringField("surname", student.getSurname());
        long bMillis = student.getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", student.getLanguage());
        jsonGenerator.writeStringField("image", student.getImage());
        long createDateMillis = student.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = student.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();
    }
}
