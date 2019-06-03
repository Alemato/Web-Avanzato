package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        lesson.setIdLesson(node.get("idLesson").asInt());                           //TODO da capire se arriva già con id

        lesson.setName(node.get("name").asText());

        lesson.setPrice(node.get("price").asDouble());

        List<Date> dateList = new ArrayList<Date>();
        ArrayNode dates = (ArrayNode) node.get("dates");
        Iterator<JsonNode> dateIterator = dates.elements();
        while (dateIterator.hasNext()){
            JsonNode dateNode = dateIterator.next();
            Date date = Date.valueOf(dateNode.get("date").asText());
            dateList.add(date);
        }
        lesson.setDate(dateList);

        lesson.setDescription(node.get("description").asText());

        lesson.setPublicationDate(Date.valueOf(node.get("publicationDate").asText()));

        List<Time> startTimeList = new ArrayList<Time>();
        ArrayNode startTimes = (ArrayNode) node.get("startTimes");
        Iterator<JsonNode> startTimeIterator = startTimes.elements();
        while (startTimeIterator.hasNext()){
            JsonNode startTimeNode = startTimeIterator.next();
            Time startTime = Time.valueOf(startTimeNode.get("startTime").asText());
            startTimeList.add(startTime);
        }
        lesson.setStartTime(startTimeList);

        List<Time> endTimeList = new ArrayList<Time>();
        ArrayNode endTimes = (ArrayNode) node.get("endTimes");
        Iterator<JsonNode> endTimeIterator = endTimes.elements();
        while (endTimeIterator.hasNext()){
            JsonNode endTimeNode = endTimeIterator.next();
            Time endTime = Time.valueOf(endTimeNode.get("endTime").asText());
            endTimeList.add(endTime);
        }
        lesson.setEndTime(endTimeList);

//        lesson.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));   //TODO da capire se arriva già
//        lesson.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));   //TODO da capire se arriva già

        Subject subject = new Subject();
        JsonNode subjectNode = node.get("idSubject");
        subject.setIdSubject(subjectNode.get("idSubject").asInt());
        subject.setMacroSubject(subjectNode.get("macroSubject").asText());
        subject.setMicroSubject(subjectNode.get("microSubject").asText());
        lesson.setSubject(subject);

        return lesson;
    }
}
