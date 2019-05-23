package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Booking;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class BookingDeserializer extends StdDeserializer<Booking> {

    public BookingDeserializer(){
        this(null);
    }

    public BookingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Booking deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Booking booking = new Booking();
//        booking.setIdBooking(node.get("idBooking").asInt());                       //TODO da capire se arriva già con id
//        booking.setDate(Date.valueOf(node.get("date").asText()));
//        booking.setLessonState(node.get("lessonState").asBoolean());
//        booking.setCreateDate(Timestamp.valueOf(node.get("createDate").asText()));  //TODO da capire se arriva già con createDate
//        booking.setUpdateDate(Timestamp.valueOf(node.get("updateDate").asText()));  //TODO da capire se arriva già con UpdateDate
//        booking.setIdStudent(node.get("idStudent").asInt());                       //TODO da capire se arriva già con id
//        booking.setIdLesson(node.get("idLesson").asInt());
        return booking;
    }
}
