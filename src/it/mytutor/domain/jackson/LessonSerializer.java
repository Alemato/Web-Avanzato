package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Lesson;

import java.io.IOException;

public class LessonSerializer extends StdSerializer<Lesson> {

    public LessonSerializer(){
        this(null);
    }

    public LessonSerializer(Class<Lesson> t) {
        super(t);
    }

    @Override
    public void serialize(Lesson lesson, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", lesson.getIdLesson());
        jsonGenerator.writeStringField("name", lesson.getName());
        jsonGenerator.writeNumberField("price", lesson.getPrice());
        jsonGenerator.writeStringField("date", lesson.getDate().toString());
        jsonGenerator.writeStringField("description", lesson.getDescription());
        jsonGenerator.writeStringField("publicationDate", lesson.getPublicationDate().toString());
        jsonGenerator.writeStringField("startTime", lesson.getStartTime().toString());
        jsonGenerator.writeStringField("endTime", lesson.getEndTime().toString());
        jsonGenerator.writeNumberField("idSubject", lesson.getIdSubject());
        jsonGenerator.writeEndObject();
    }

}
