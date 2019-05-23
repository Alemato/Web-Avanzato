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

        jsonGenerator.writeStartObject();
//        jsonGenerator.writeNumberField("idBooking", booking.getIdBooking());
//        jsonGenerator.writeStringField("date", booking.getDate().toString());
//        jsonGenerator.writeBooleanField("lessonState", booking.isLessonState());
//        jsonGenerator.writeStringField("createDate", booking.getCreateDate().toString());
//        jsonGenerator.writeStringField("updateDate", booking.getUpdateDate().toString());
//        jsonGenerator.writeNumberField("idStudent", booking.getIdStudent());
//        jsonGenerator.writeNumberField("idLesson", booking.getIdLesson());
        jsonGenerator.writeEndObject();
    }
}
