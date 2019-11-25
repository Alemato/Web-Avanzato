package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Student;

import java.io.IOException;
import java.sql.Date;

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
        if (node.get("idStudent")!=  null) {
            student.setIdStudent(node.get("idStudent").asInt());
        }
        student.setStudyGrade(node.get("studyGrade").asText());
        JsonNode userNode = node.get("user");
        student.setIdUser(userNode.get("idUser").asInt());
        student.setEmail(userNode.get("email").asText());
        student.setName(userNode.get("name").asText());
        student.setSurname(userNode.get("surname").asText());
        Date bDate = new Date(userNode.get("birthday").asLong());
        student.setBirthday(bDate);
        student.setLanguage(Boolean.getBoolean(userNode.get("language").asText()));
        student.setImage(userNode.get("image").asText());
        return student;
    }
}
