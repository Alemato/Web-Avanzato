package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.mytutor.domain.Booking;

import java.io.IOException;

public class BookingSerializer extends StdSerializer<Booking> {

    public BookingSerializer(){
        this(null);
    }

    public BookingSerializer(Class<Booking> t) {
        super(t);
    }

    @Override
    public void serialize(Booking booking, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idBooking", booking.getIdBooking());
        long dateMillis = booking.getDate().getTime();
        jsonGenerator.writeNumberField("date", dateMillis);
        jsonGenerator.writeNumberField("lessonState", booking.getLessonState());
        long createDate = booking.getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate);
        long updateDate = booking.getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate);
        jsonGenerator.writeFieldName("student");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idStudent", booking.getStudent().getIdStudent());
        jsonGenerator.writeStringField("studyGrade", booking.getStudent().getStudyGrade());
        jsonGenerator.writeNumberField("idUser", booking.getStudent().getIdUser());

        jsonGenerator.writeNumberField("idUser", booking.getStudent().getIdUser());
        jsonGenerator.writeStringField("email", booking.getStudent().getEmail());
        jsonGenerator.writeStringField("name", booking.getStudent().getName());
        jsonGenerator.writeStringField("surname", booking.getStudent().getSurname());
        long bMillis = booking.getStudent().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis);
        jsonGenerator.writeBooleanField("language", booking.getStudent().getLanguage());
        jsonGenerator.writeStringField("image", booking.getStudent().getImage());
        long createDate1 = booking.getStudent().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDate1);
        long updateDate1 = booking.getStudent().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDate1);

        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("planning");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idPlanning", booking.getPlanning().getIdPlanning());
        long bMillis1 = booking.getPlanning().getDate().getTime();
        jsonGenerator.writeNumberField("date", bMillis1);
        jsonGenerator.writeStringField("startTime", booking.getPlanning().getStartTime().toString());
        jsonGenerator.writeStringField("endTime", booking.getPlanning().getEndTime().toString());
        jsonGenerator.writeBooleanField("available", booking.getPlanning().getAvailable());
        long createDatePlanning = booking.getPlanning().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDatePlanning);
        long updateDatePlanning = booking.getPlanning().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDatePlanning);

        jsonGenerator.writeFieldName("lesson");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idLesson", booking.getPlanning().getLesson().getIdLesson());
        jsonGenerator.writeStringField("name", booking.getPlanning().getLesson().getName());
        jsonGenerator.writeNumberField("price", booking.getPlanning().getLesson().getPrice());
        jsonGenerator.writeStringField("description", booking.getPlanning().getLesson().getDescription());
        long publicationDateMillis = booking.getPlanning().getLesson().getPublicationDate().getTime();
        jsonGenerator.writeNumberField("publicationDate", publicationDateMillis);
        long lessonCreateDate = booking.getPlanning().getLesson().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", lessonCreateDate);
        long lessonUpdateDate = booking.getPlanning().getLesson().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", lessonUpdateDate);

        jsonGenerator.writeFieldName("subject");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idSubject", booking.getPlanning().getLesson().getSubject().getIdSubject());
        jsonGenerator.writeStringField("macroSubject", booking.getPlanning().getLesson().getSubject().getMacroSubject());
        jsonGenerator.writeStringField("microSubject", booking.getPlanning().getLesson().getSubject().getMicroSubject());
        long createDateMillis = booking.getPlanning().getLesson().getSubject().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis);
        long updateDateMillis = booking.getPlanning().getLesson().getSubject().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis);
        jsonGenerator.writeEndObject();

        jsonGenerator.writeFieldName("teacher");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idTeacher", booking.getPlanning().getLesson().getTeacher().getIdTeacher());
        jsonGenerator.writeNumberField("postCode", booking.getPlanning().getLesson().getTeacher().getPostCode());
        jsonGenerator.writeStringField("City", booking.getPlanning().getLesson().getTeacher().getCity());
        jsonGenerator.writeStringField("region", booking.getPlanning().getLesson().getTeacher().getRegion());
        jsonGenerator.writeStringField("street", booking.getPlanning().getLesson().getTeacher().getStreet());
        jsonGenerator.writeStringField("streetNumber", booking.getPlanning().getLesson().getTeacher().getStreetNumber());
        jsonGenerator.writeStringField("byography", booking.getPlanning().getLesson().getTeacher().getByography());

        jsonGenerator.writeNumberField("idUser", booking.getPlanning().getLesson().getTeacher().getIdUser());
        jsonGenerator.writeStringField("email", booking.getPlanning().getLesson().getTeacher().getEmail());
        jsonGenerator.writeNumberField("roles", booking.getPlanning().getLesson().getTeacher().getRoles());
        jsonGenerator.writeStringField("name", booking.getPlanning().getLesson().getTeacher().getName());
        jsonGenerator.writeStringField("surname", booking.getPlanning().getLesson().getTeacher().getSurname());
        long bMillis2 = booking.getPlanning().getLesson().getTeacher().getBirthday().getTime();
        jsonGenerator.writeNumberField("birthday", bMillis2);
        jsonGenerator.writeBooleanField("language", booking.getPlanning().getLesson().getTeacher().getLanguage());
        jsonGenerator.writeStringField("image", booking.getPlanning().getLesson().getTeacher().getImage());
        long createDateMillis1 = booking.getPlanning().getLesson().getTeacher().getCreateDate().getTime();
        jsonGenerator.writeNumberField("createDate", createDateMillis1);
        long updateDateMillis1 = booking.getPlanning().getLesson().getTeacher().getUpdateDate().getTime();
        jsonGenerator.writeNumberField("updateDate", updateDateMillis1);

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
