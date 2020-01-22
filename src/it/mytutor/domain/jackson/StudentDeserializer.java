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
        student.setIdUser(node.get("idUser").asInt());
        student.setEmail(node.get("email").asText());
        student.setRoles(node.get("roles").asInt());
        student.setPassword(node.get("password").asText());
        student.setName(node.get("name").asText());
        student.setSurname(node.get("surname").asText());
        Date bDate = new Date(node.get("birthday").asLong());
        student.setBirthday(bDate);
        student.setLanguage(node.get("language").asBoolean());
        if(node.get("image").asText().equals("null") || node.get("image").asText().equals("")){
            student.setImage(null);
        } else student.setImage(node.get("image").asText());
        return student;
    }
}
