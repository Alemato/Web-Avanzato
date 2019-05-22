package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Lesson;

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
        lesson.setIdLesson(node.get("idLesson").asLong());        //TODO da capire se arriva già con id
        lesson.setName(node.get("nome").asText());
        lesson.setPrice(node.get("price").asDouble());
        lesson.setDate(Date.valueOf(node.get("date").asText()));
        lesson.setDescription(node.get("description").asText());
        lesson.setPublicationDate(Date.valueOf(node.get("publicationDate").asText()));
        lesson.setStartTime(Timestamp.valueOf(node.get("startTime").asText()));
        lesson.setEndTime(Timestamp.valueOf(node.get("endTime").asText()));
        lesson.setIdSubject(node.get("idSubject").asLong());     //TODO da capire se arriva già con id

        return lesson;
    }
}
