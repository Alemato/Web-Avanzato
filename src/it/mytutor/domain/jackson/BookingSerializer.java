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
        jsonGenerator.writeStringField("date", booking.getDate().toString());
        jsonGenerator.writeNumberField("lessonState", booking.getLessonState());
        jsonGenerator.writeStringField("createDate", booking.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", booking.getUpdateDate().toString());
        jsonGenerator.writeFieldName("student");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("studentIdStudent", booking.getStudent().getIdStudent());
        jsonGenerator.writeStringField("studentName", booking.getStudent().getName());
        jsonGenerator.writeStringField("studentSurname", booking.getStudent().getSurname());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("lesson");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("lessonIdLesson", booking.getLesson().getIdLesson());
        jsonGenerator.writeStringField("lessonName", booking.getLesson().getName());
        jsonGenerator.writeNumberField("lessonPrice", booking.getLesson().getPrice());
        jsonGenerator.writeStringField("lessonPublicationDate", booking.getLesson().getPublicationDate().toString());
        jsonGenerator.writeStringField("lessonCreateDate", booking.getLesson().getCreateDate().toString());
        jsonGenerator.writeStringField("lessonUpdateDate", booking.getLesson().getUpdateDate().toString());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeFieldName("planning");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idPlanning", booking.getPlanning().getIdPlanning());
        jsonGenerator.writeStringField("startTime", booking.getPlanning().getStartTime().toString());
        jsonGenerator.writeStringField("endTime", booking.getPlanning().getEndTime().toString());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
