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

        jsonGenerator.writeStringField("createDate", student.getCreateDateStudent().toString());
        jsonGenerator.writeStringField("updateDate", student.getUpdateDateStudent().toString());

        jsonGenerator.writeNumberField("idUser", student.getIdUser());
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
