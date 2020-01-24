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
        jsonGenerator.writeNumberField("postCode", lesson.getTeacher().getPostCode());
        jsonGenerator.writeStringField("city", lesson.getTeacher().getCity());
        jsonGenerator.writeStringField("region", lesson.getTeacher().getRegion());
        jsonGenerator.writeStringField("street", lesson.getTeacher().getStreet());
        jsonGenerator.writeStringField("streetNumber", lesson.getTeacher().getStreetNumber());
        jsonGenerator.writeStringField("byography", lesson.getTeacher().getByography());
        long crateDateTeacherMillis = lesson.getTeacher().getCrateDateTeacher().getTime();
        jsonGenerator.writeNumberField("crateDate", crateDateTeacherMillis);
        long updateDateTeacherMillis = lesson.getTeacher().getUpdateDateTeacher().getTime();
        jsonGenerator.writeNumberField("updateDate",updateDateTeacherMillis);

        jsonGenerator.writeNumberField("idUser", lesson.getTeacher().getIdUser());
        jsonGenerator.writeStringField("email", lesson.getTeacher().getEmail());
        jsonGenerator.writeNumberField("roles", lesson.getTeacher().getRoles());
        jsonGenerator.writeStringField("name", lesson.getTeacher().getName());
        jsonGenerator.writeStringField("surname", lesson.getTeacher().getSurname());
        long bMillis = lesson.getTeacher().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", lesson.getTeacher().getLanguage());
        jsonGenerator.writeStringField("image", lesson.getTeacher().getImage());
        long createDateMillis = lesson.getTeacher().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = lesson.getTeacher().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}
