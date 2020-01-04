package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;

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

        return lesson;
    }
}
