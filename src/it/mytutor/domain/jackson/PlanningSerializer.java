package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Planning;

import java.io.IOException;

public class PlanningSerializer extends StdSerializer<Planning> {

    public PlanningSerializer() {
        this(null);
    }

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
        jsonGenerator.writeBooleanField("available", planning.getAvailable());
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
        jsonGenerator.writeNumberField("createDate", lessonCreateDate);
        long lessonUpdateDate = planning.getLesson().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", lessonUpdateDate);

        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idSubject", planning.getLesson().getSubject().getIdSubject());
        jsonGenerator.writeStringField("macroSubject", planning.getLesson().getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", planning.getLesson().getSubject().getMicroSubject());
        long subjectCreateDate = planning.getLesson().getSubject().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", subjectCreateDate);
        long subjectUpdateDate = planning.getLesson().getSubject().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", subjectUpdateDate);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeFieldName("teacher");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idTeacher", planning.getLesson().getTeacher().getIdTeacher());
        jsonGenerator.writeNumberField("postCode", planning.getLesson().getTeacher().getPostCode());
        jsonGenerator.writeStringField("City", planning.getLesson().getTeacher().getCity());
        jsonGenerator.writeStringField("region", planning.getLesson().getTeacher().getRegion());
        jsonGenerator.writeStringField("street", planning.getLesson().getTeacher().getStreet());
        jsonGenerator.writeStringField("streetNumber", planning.getLesson().getTeacher().getStreetNumber());
        jsonGenerator.writeStringField("byography", planning.getLesson().getTeacher().getByography());
        long crateDateTeacherMillis = planning.getLesson().getTeacher().getCrateDateTeacher().getTime();
        jsonGenerator.writeNumberField("crateDate", crateDateTeacherMillis);
        long updateDateTeacherMillis = planning.getLesson().getTeacher().getUpdateDateTeacher().getTime();
        jsonGenerator.writeNumberField("updateDate",updateDateTeacherMillis);

        jsonGenerator.writeNumberField("idUser", planning.getLesson().getTeacher().getIdUser());
        jsonGenerator.writeStringField("email", planning.getLesson().getTeacher().getEmail());
        jsonGenerator.writeNumberField("roles", planning.getLesson().getTeacher().getRoles());
        jsonGenerator.writeStringField("name", planning.getLesson().getTeacher().getName());
        jsonGenerator.writeStringField("surname", planning.getLesson().getTeacher().getSurname());
        long bMillis = planning.getLesson().getTeacher().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", planning.getLesson().getTeacher().getLanguage());
        jsonGenerator.writeStringField("image", planning.getLesson().getTeacher().getImage());
        long createDateMillis = planning.getLesson().getTeacher().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = planning.getLesson().getTeacher().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
