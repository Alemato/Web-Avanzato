package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

public class PlanningDeserializer  extends StdDeserializer<Planning> {

    public PlanningDeserializer(){
        this(null);
    }

    public PlanningDeserializer(Class<?> vc) {super(vc); }

    @Override
    public Planning deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Planning planning = new Planning();
        planning.setIdPlanning(node.get("idPlanning").asInt());
        planning.setDate(Date.valueOf(node.get("date").asText()));
        planning.setStartTime(Time.valueOf(node.get("startTime").asText()));
        planning.setEndTime(Time.valueOf(node.get("endTime").asText()));

        Lesson lesson = new Lesson();
        lesson.setIdLesson(node.get("idLesson").asInt());
        planning.setLesson(lesson);

        return planning;
    }




}
