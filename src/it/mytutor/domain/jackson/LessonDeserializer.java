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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List<Date> dateList = new ArrayList<Date>();
        ArrayNode dates = (ArrayNode) node.get("dates");
        Iterator<JsonNode> dateIterator = dates.elements();
        while (dateIterator.hasNext()){
            JsonNode dateNode = dateIterator.next();
            String stringDate = String.valueOf(dateNode);

            stringDate = stringDate.replace("\"","");


            java.util.Date parsed = null;
            try {
                parsed = format.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date sqlDate = new Date(parsed.getTime());
            dateList.add(sqlDate);
        }
        lesson.setDate(dateList);

        lesson.setDescription(node.get("description").asText());

        lesson.setPublicationDate(Date.valueOf(node.get("publicationDate").asText()));

        List<Time> startTimeList = new ArrayList<Time>();
        ArrayNode startTimes = (ArrayNode) node.get("startTimes");
        System.out.println(startTimes.size());
        for (JsonNode startTimeNode : startTimes) {
            String stringStartTime = String.valueOf(startTimeNode);
            stringStartTime = stringStartTime.replace("\"", "");
            Time time = Time.valueOf(stringStartTime);
            startTimeList.add(time);
        }

        lesson.setStartTime(startTimeList);
        System.out.println(lesson.getStartTime());
        System.out.println(lesson.getStartTime().size());
        List<Time> endTimeList = new ArrayList<Time>();
        ArrayNode endTimes = (ArrayNode) node.get("endTimes");
        Iterator<JsonNode> endTimeIterator = endTimes.iterator();
        while (endTimeIterator.hasNext()){
            JsonNode endTimeNode = endTimeIterator.next();
            String stringEndTime = String.valueOf(endTimeNode);
            stringEndTime = stringEndTime.replace("\"","");
            Time time = Time.valueOf(stringEndTime);
            endTimeList.add(time);
        }
        lesson.setEndTime(endTimeList);

//        lesson.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));   //TODO da capire se arriva già
//        lesson.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));   //TODO da capire se arriva già

        Subject subject = new Subject();
        JsonNode subjectNode = node.get("subject");
        subject.setIdSubject(subjectNode.get("idSubject").asInt());
        subject.setMacroSubject(subjectNode.get("macroSubject").asText());
        subject.setMicroSubject(subjectNode.get("microSubject").asText());
        lesson.setSubject(subject);

        return lesson;
    }
}
