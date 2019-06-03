package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.io.IOException;

public class TeacherDeserializer extends StdDeserializer<Teacher> {
    public TeacherDeserializer(){ this(null); }

    public TeacherDeserializer(Class<?> vc) { super(vc); }

    @Override
    public Teacher deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Teacher teacher = new Teacher();
        teacher.setIdTeacher(node.get("idTeacher").asInt());

        teacher.setPostCode(node.get("postCode").asInt());

        teacher.setCity(node.get("City").asText());

        teacher.setRegion(node.get("region").asText());

        teacher.setStreet(node.get("street").asText());

        teacher.setStreetNumber(node.get("streetNumber").asText());

        teacher.setByography(node.get("byography").asText());

        return teacher;
    }


}
