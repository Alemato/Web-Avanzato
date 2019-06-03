package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Student;

import java.io.IOException;
import java.sql.Timestamp;

public class StudentDeserializer extends StdDeserializer<Student> {

    public StudentDeserializer(){
        this(null);
    }

    public StudentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Student deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Student student = new Student();
        student.setIdStudent(node.get("idStudent").asInt());

        student.setStudyGrade(node.get("studyGrade").asText());

//        student.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));
//        student.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));

        student.setIdUser(node.get("idUser").asInt());
        return student;
    }
}
