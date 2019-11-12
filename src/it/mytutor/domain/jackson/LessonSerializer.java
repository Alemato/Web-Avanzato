package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Lesson;

import java.io.IOException;

public class LessonSerializer extends StdSerializer<Lesson> {

    public LessonSerializer() {
        this(null);
    }

    public LessonSerializer(Class<Lesson> t) {
        super(t);
    }

    @Override
    public void serialize(Lesson lesson, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idLesson", lesson.getIdLesson());
        jsonGenerator.writeStringField("name", lesson.getName());
        jsonGenerator.writeNumberField("price", lesson.getPrice());
        jsonGenerator.writeStringField("description", lesson.getDescription());
        long millis = lesson.getPublicationDate().getTime();
        jsonGenerator.writeNumberField("publicationDate", millis);
        long createDate = lesson.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = lesson.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idSubject", lesson.getSubject().getIdSubject().toString());
        jsonGenerator.writeStringField("macroSubject", lesson.getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", lesson.getSubject().getMicroSubject());
        long subjectCreateDate = lesson.getSubject().getCreateDate().getTime();
        jsonGenerator.writeNumberField("subjectCreateDate", subjectCreateDate);
        long subjectUpdateDate = lesson.getSubject().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("subjectUpdateDate", subjectUpdateDate);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("teacher");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idTeacher", lesson.getTeacher().getIdTeacher());
        jsonGenerator.writeStringField("name", lesson.getTeacher().getName());
        jsonGenerator.writeStringField("surname", lesson.getTeacher().getSurname());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
