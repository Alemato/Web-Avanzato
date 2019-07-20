package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Subject;

import java.io.IOException;

public class SubjectSerializer extends StdSerializer<Subject> {
    public SubjectSerializer(){
        this(null);
    }

    public SubjectSerializer(Class<Subject> t) {
        super(t);
    }

    @Override
    public void serialize(Subject subject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idSubject", subject.getIdSubject());
        jsonGenerator.writeStringField("macroSubject", subject.getMacroSubject());
        jsonGenerator.writeStringField("microSubject", subject.getMicroSubject());
        long createDateMillis = subject.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = subject.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
