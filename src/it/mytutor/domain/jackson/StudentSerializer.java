package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
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
    public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject("student");

        jsonGenerator.writeNumberField("idStudent", student.getIdStudent());

        jsonGenerator.writeStringField("studyGrade", student.getStudyGrade());

        jsonGenerator.writeStringField("createDateStudent", student.getCreateDateStudent().toString());
        jsonGenerator.writeStringField("updateDateStudent", student.getUpdateDateStudent().toString());

        jsonGenerator.writeFieldName("user");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idUser", student.getIdUser());
        jsonGenerator.writeStringField("email", student.getEmail());
        jsonGenerator.writeStringField("name", student.getName());
        jsonGenerator.writeStringField("surname", student.getSurname());
        jsonGenerator.writeStringField("birthday", student.getBirthday().toString());
        jsonGenerator.writeBooleanField("language", student.getLanguage());
        jsonGenerator.writeStringField("image", student.getImage());
        jsonGenerator.writeStringField("createDate", student.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", student.getUpdateDate().toString());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
