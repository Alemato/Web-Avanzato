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
        long dateMillis = planning.getDate().getTime();
        jsonGenerator.writeNumberField("date", dateMillis);
        jsonGenerator.writeStringField("startTime", planning.getStartTime().toString());
        jsonGenerator.writeStringField("endTime", planning.getEndTime().toString());
        long createDate = planning.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = planning.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeFieldName("lesson");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idLesson", planning.getLesson().getIdLesson());
        jsonGenerator.writeStringField("name", planning.getLesson().getName());
        jsonGenerator.writeNumberField("price", planning.getLesson().getPrice());
        jsonGenerator.writeStringField("description", planning.getLesson().getDescription());
        long pDateMillis = planning.getLesson().getPublicationDate().getTime();
        jsonGenerator.writeNumberField("publicationDate", pDateMillis);
        long lessonCreateDate = planning.getLesson().getCreateDate().getTime();
        jsonGenerator.writeNumberField("lessonCreateDate", lessonCreateDate);
        long lessonUpdateDate = planning.getLesson().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("lessonUpdateDate", lessonUpdateDate);
        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idSubject", planning.getLesson().getSubject().getIdSubject().toString());
        jsonGenerator.writeStringField("macroSubject", planning.getLesson().getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", planning.getLesson().getSubject().getMicroSubject());
        long subjectCreateDate = planning.getLesson().getSubject().getCreateDate().getTime();
        jsonGenerator.writeNumberField("subjectCreateDate", subjectCreateDate);
        long subjectUpdateDate = planning.getLesson().getSubject().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("subjectUpdateDate", subjectUpdateDate);
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
