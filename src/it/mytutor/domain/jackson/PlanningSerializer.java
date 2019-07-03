package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Planning;

import java.io.IOException;

public class PlanningSerializer extends StdSerializer<Planning> {

    public PlanningSerializer() { this(null); }

    public PlanningSerializer(Class<Planning> t) {
        super(t);
    }

    @Override
    public void serialize(Planning planning, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idPlanning", planning.getIdPlanning());
        jsonGenerator.writeStringField("date", planning.getDate().toString());
        jsonGenerator.writeStringField("startTime", planning.getStartTime().toString());
        jsonGenerator.writeStringField("endTime", planning.getEndTime().toString());
        jsonGenerator.writeStringField("createDate", planning.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", planning.getUpdateDate().toString());
        jsonGenerator.writeFieldName("lesson");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idLesson", planning.getLesson().getIdLesson());
        jsonGenerator.writeStringField("name", planning.getLesson().getName());
        jsonGenerator.writeNumberField("price", planning.getLesson().getPrice());
        jsonGenerator.writeStringField("description", planning.getLesson().getDescription());
        jsonGenerator.writeStringField("publicationDate", planning.getLesson().getPublicationDate().toString());
        jsonGenerator.writeStringField("lessonCreateDate", planning.getLesson().getCreateDate().toString());
        jsonGenerator.writeStringField("lessonUpdateDate", planning.getLesson().getUpdateDate().toString());
        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idSubject", planning.getLesson().getSubject().getIdSubject().toString());
        jsonGenerator.writeStringField("macroSubject", planning.getLesson().getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", planning.getLesson().getSubject().getMicroSubject());
        jsonGenerator.writeStringField("subjectCreateTime", planning.getLesson().getSubject().getCreateDate().toString());
        jsonGenerator.writeStringField("subjectUpdateTime", planning.getLesson().getSubject().getUpdateDate().toString());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
