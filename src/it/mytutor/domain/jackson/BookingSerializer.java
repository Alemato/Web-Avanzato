package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
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
    public void serialize(Booking booking, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject("booking");
        jsonGenerator.writeNumberField("idBooking", booking.getIdBooking());
        jsonGenerator.writeStringField("date", booking.getDate().toString());
        jsonGenerator.writeNumberField("lessonState", booking.getLessonState());
        jsonGenerator.writeStringField("createDate", booking.getCreateDate().toString());
        jsonGenerator.writeStringField("updateDate", booking.getUpdateDate().toString());

        jsonGenerator.writeFieldName("student");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("idStudent", booking.getStudent().getIdStudent().toString());
        jsonGenerator.writeStringField("StudyGrade", booking.getStudent().getStudyGrade());
        jsonGenerator.writeStringField("CreateDateStudent", booking.getStudent().getCreateDateStudent().toString());
        jsonGenerator.writeStringField("UpdateDateStudent", booking.getStudent().getUpdateDateStudent().toString());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeFieldName("lesson");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("IdLesson", booking.getLesson().getIdLesson().toString());
        //TODO da vedere se serve Tutto l'oggetto lesson
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}
