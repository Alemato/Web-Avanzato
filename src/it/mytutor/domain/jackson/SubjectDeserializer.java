package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Subject;

import java.io.IOException;
import java.sql.Timestamp;

public class SubjectDeserializer extends StdDeserializer<Subject> {
    public SubjectDeserializer(){
        this(null);
    }

    public SubjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Subject deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Subject subject = new Subject();
        subject.setIdSubject(node.get("idSubject").asInt());
        subject.setMacroSubject(node.get("macroSubject").asText());
        subject.setMicroSubject(node.get("microSubject").asText());
        subject.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));
        subject.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));
        return null;
    }
}
