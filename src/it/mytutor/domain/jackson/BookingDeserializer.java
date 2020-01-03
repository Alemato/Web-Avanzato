package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Student;

import java.io.IOException;
import java.sql.Date;

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
        Date date = new Date(node.get("date").asLong());
        booking.setDate(date);

        booking.setLessonState(node.get("lessonState").asInt());

//        Student student = new Student();
//        student.setIdStudent(node.findPath("idStudent").asInt());
//        booking.setStudent(student);

        Planning planning = new Planning();
        planning.setIdPlanning(node.findPath("idPlanning").asInt());

//        Lesson lesson = new Lesson();
//        lesson.setIdLesson(node.findPath("idLesson").asInt());
//        planning.setLesson(lesson);

        booking.setPlanning(planning);

        return booking;
    }
}