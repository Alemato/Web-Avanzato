package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Teacher;

import java.io.IOException;
import java.sql.Date;

public class TeacherDeserializer extends StdDeserializer<Teacher> {
    public TeacherDeserializer(){ this(null); }

    public TeacherDeserializer(Class<?> vc) { super(vc); }

    @Override
    public Teacher deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Teacher teacher = new Teacher();
        if (node.get("idTeacher") != null) {
            teacher.setIdTeacher(node.get("idTeacher").asInt());
        }
        teacher.setPostCode(node.get("postCode").asInt());
        teacher.setCity(node.get("city").asText());
        teacher.setRegion(node.get("region").asText());
        teacher.setStreet(node.get("street").asText());
        teacher.setStreetNumber(node.get("streetNumber").asText());
        teacher.setByography(node.get("byography").asText());
        teacher.setIdUser(node.get("idUser").asInt());
        teacher.setEmail(node.get("email").asText());
        teacher.setPassword(node.get("password").asText());
        teacher.setName(node.get("name").asText());
        teacher.setSurname(node.get("surname").asText());
        Date bDate = new Date(node.get("birthday").asLong());
        teacher.setBirthday(bDate);
        teacher.setLanguage(node.get("language").asBoolean());
        if(node.get("image").asText().equals("null") || node.get("image").asText().equals("")){
            teacher.setImage(null);
        } else teacher.setImage(node.get("image").asText());

        return teacher;
    }


}
