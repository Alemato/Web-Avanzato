package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
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
    public void serialize(Planning planning, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject("planning");
        jsonGenerator.writeNumberField("idPlanning", planning.getIdPlanning());
        jsonGenerator.writeStringField("date", planning.getDate().toString());
        jsonGenerator.writeStringField("startTime", planning.getStartTime().toString());
        jsonGenerator.writeStringField("endTime", planning.getEndTime().toString());
        jsonGenerator.writeStringField("createDate", planning.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", planning.getUpdateDate().toString());

        jsonGenerator.writeStringField("idLesson", planning.getLesson().getIdLesson().toString());


    }
}
