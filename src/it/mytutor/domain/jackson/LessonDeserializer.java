package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class LessonDeserializer extends StdDeserializer<Lesson> {

    public LessonDeserializer(){
        this(null);
    }

    public LessonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Lesson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Lesson lesson = new Lesson();

        if (node.get("idLesson") != null) {
            lesson.setIdLesson(node.get("idLesson").asInt());
        }
        lesson.setName(node.get("name").asText());
        lesson.setPrice(node.get("price").asDouble());
        lesson.setDescription(node.get("description").asText());
        lesson.setPublicationDate(new Date(node.get("publicationDate").asLong()));
        Subject subject = new Subject();
        JsonNode subjectNode = node.get("subject");
        if (subjectNode.get("idSubject") != null) {
            subject.setIdSubject(subjectNode.get("idSubject").asInt());
        }
        subject.setMacroSubject(subjectNode.get("macroSubject").asText());
        subject.setMicroSubject(subjectNode.get("microSubject").asText());
        lesson.setSubject(subject);

        JsonNode teacherNode = node.get("teacher");
        Teacher teacher = new Teacher();
        if (teacherNode.get("idTeacher") != null) {
            teacher.setIdTeacher(teacherNode.get("idTeacher").asInt());
        }
        teacher.setPostCode(teacherNode.get("postCode").asInt());
        teacher.setCity(teacherNode.get("city").asText());
        teacher.setRegion(teacherNode.get("region").asText());
        teacher.setStreet(teacherNode.get("street").asText());
        teacher.setStreetNumber(teacherNode.get("streetNumber").asText());
        teacher.setByography(teacherNode.get("byography").asText());
        teacher.setIdUser(teacherNode.get("idUser").asInt());
        teacher.setEmail(teacherNode.get("email").asText());
        teacher.setName(teacherNode.get("name").asText());
        teacher.setSurname(teacherNode.get("surname").asText());
        Date bDate = new Date(teacherNode.get("birthday").asLong());
        teacher.setBirthday(bDate);
        teacher.setLanguage(Boolean.getBoolean(teacherNode.get("language").asText()));
        if(teacherNode.get("image").asText().equals("null") || teacherNode.get("image").asText().equals("")){
            teacher.setImage(null);
        } else teacher.setImage(teacherNode.get("image").asText());
        lesson.setTeacher(teacher);

        return lesson;
    }
}
