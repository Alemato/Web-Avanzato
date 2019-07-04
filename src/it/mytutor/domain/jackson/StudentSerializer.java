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
        long createDate = student.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = student.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
