package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Student;

import java.io.IOException;
import java.sql.Date;
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
        //TODO controllo per vedere se ricevere l'id oppure no, cioè se PUT o POST
        student.setIdStudent(node.get("idStudent").asInt());

        student.setStudyGrade(node.get("studyGrade").asText());
        JsonNode userNode = node.get("user");
        student.setIdUser(userNode.get("idUser").asInt());
        student.setEmail(userNode.get("email").asText());
        student.setName(userNode.get("name").asText());
        student.setSurname(userNode.get("surname").asText());
        student.setBirtday(Date.valueOf(userNode.get("birthday").asText()));
        student.setLanguage(Boolean.getBoolean(userNode.get("language").asText()));
        student.setImage(userNode.get("image").asText());
        return student;
    }
}
