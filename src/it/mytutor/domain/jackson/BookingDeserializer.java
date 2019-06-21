package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingDeserializer extends StdDeserializer<Booking> {

    public BookingDeserializer(){
        this(null);
    }

    public BookingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Booking deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Booking booking = new Booking();

        booking.setIdBooking(node.get("idBooking").asInt());                       //TODO da capire se arriva già con id

        booking.setDate(Date.valueOf(node.get("date").asText()));

        booking.setLessonState(node.get("lessonState").asInt());

        Student student = new Student();
        student.setIdStudent(node.findPath("idStudent").asInt());
        booking.setStudent(student);

        Lesson lesson = new Lesson();
        lesson.setIdLesson(node.findPath("idLesson").asInt());

        booking.setLesson(lesson);

        return booking;
    }
}