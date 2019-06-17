package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Lesson;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

public class LessonSerializer extends StdSerializer<Lesson> {

    public LessonSerializer(){
        this(null);
    }

    public LessonSerializer(Class<Lesson> t) {
        super(t);
    }

    @Override
    public void serialize(Lesson lesson, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject("lesson");
        jsonGenerator.writeNumberField("idLesson", lesson.getIdLesson());

        jsonGenerator.writeStringField("name", lesson.getName());

        jsonGenerator.writeNumberField("price", lesson.getPrice());

        jsonGenerator.writeFieldName("dates");
        jsonGenerator.writeStartArray();
        for (Date date: lesson.getDate()){
            jsonGenerator.writeString(date.toString());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("description", lesson.getDescription());

        jsonGenerator.writeStringField("publicationDate", lesson.getPublicationDate().toString());

        jsonGenerator.writeFieldName("startTimes");
        jsonGenerator.writeStartArray();
        for (Time startTime: lesson.getStartTime()){
            jsonGenerator.writeString(startTime.toString());
        }
        jsonGenerator.writeEndArray();


        jsonGenerator.writeFieldName("endTimes");
        jsonGenerator.writeStartArray();
        for (Time endTime: lesson.getEndTime()){
            jsonGenerator.writeString(endTime.toString());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeStringField("createDate",lesson.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate",lesson.getUpdateDate().toString());

        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject("subject");
        jsonGenerator.writeStringField("idSubject", lesson.getSubject().getIdSubject().toString());
        jsonGenerator.writeStringField("macroSubject", lesson.getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", lesson.getSubject().getMicroSubject());
        jsonGenerator.writeStringField("createTime", lesson.getSubject().getCreateDate().toString());
        jsonGenerator.writeStringField("updateTime", lesson.getSubject().getUpdateDate().toString());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();

        jsonGenerator.close();
    }

}
