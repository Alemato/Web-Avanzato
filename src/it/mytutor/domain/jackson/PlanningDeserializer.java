package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Subject;

import java.io.IOException;
import java.sql.Date;

public class PlanningDeserializer  extends StdDeserializer<Planning> {

    public PlanningDeserializer(){
        this(null);
    }

    public PlanningDeserializer(Class<?> vc) {super(vc); }

    @Override
    public Planning deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Planning planning = new Planning();
        if (node.get("idPlanning") != null) {
            planning.setIdPlanning(node.get("idPlanning").asInt());
        }
        Date date = new Date(node.get("date").asLong());
        planning.setDate(date);
        planning.setStartTime(java.sql.Time.valueOf(node.get("startTime").asText()));
        planning.setEndTime(java.sql.Time.valueOf(node.get("endTime").asText()));
        planning.setAvailable(Boolean.getBoolean(node.get("available").asText()));
        planning.setRepeatPlanning(Boolean.getBoolean(node.get("repeatPlanning").asText()));

        Lesson lesson = new Lesson();
        if (node.findPath("lesson").findPath("idLesson") != null){
            lesson.setIdLesson(node.findPath("lesson").findPath("idLesson").asInt());
        }
        lesson.setName(node.findPath("lesson").findPath("name").asText());
        lesson.setPrice(node.findPath("lesson").findPath("price").asDouble());
        lesson.setDescription(node.findPath("lesson").findPath("description").asText());
        Date pDate = new Date(node.findPath("lesson").findPath("publicationDate").asLong());
        lesson.setPublicationDate(pDate);

        Subject subject = new Subject();
        subject.setMacroSubject(node.findPath("lesson").findPath("subject").findPath("macroSubject").asText());
        subject.setMicroSubject(node.findPath("lesson").findPath("subject").findPath("microSubject").asText());
        lesson.setSubject(subject);

        planning.setLesson(lesson);

        return planning;
    }




}
