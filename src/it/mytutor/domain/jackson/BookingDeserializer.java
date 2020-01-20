package it.mytutor.domain.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import it.mytutor.domain.*;

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
        if(node.get("idBooking") != null){
            booking.setIdBooking(node.get("idBooking").asInt());
        }
        Date bookDate = new Date(node.get("date").asLong());
        booking.setDate(bookDate);
        booking.setLessonState(node.get("lessonState").asInt());

        Student student = new Student();
        if (node.findPath("student").findPath("idStudent")!=  null) {
            student.setIdStudent(node.findPath("student").findPath("idStudent").asInt());
        }
        student.setStudyGrade(node.findPath("student").findPath("studyGrade").asText());
        student.setIdUser(node.findPath("student").findPath("idUser").asInt());
        student.setEmail(node.findPath("student").findPath("email").asText());
        student.setRoles(node.findPath("student").findPath("roles").asInt());
        student.setName(node.findPath("student").findPath("name").asText());
        student.setSurname(node.findPath("student").findPath("surname").asText());
        Date birthdayStudentDate = new Date(node.findPath("student").findPath("birthday").asLong());
        student.setBirthday(birthdayStudentDate);
        student.setLanguage(Boolean.getBoolean(node.findPath("student").findPath("language").asText()));
        if(node.findPath("student").findPath("image").asText().equals("null")){
            student.setImage(null);
        } else student.setImage(node.findPath("student").findPath("image").asText());

        booking.setStudent(student);

        Planning planning = new Planning();
        if (node.findPath("planning").findPath("idPlanning") != null) {
            planning.setIdPlanning(node.findPath("planning").findPath("idPlanning").asInt());
        }
        Date date = new Date(node.findPath("planning").findPath("date").asLong());
        planning.setDate(date);
        planning.setStartTime(java.sql.Time.valueOf(node.findPath("planning").findPath("startTime").asText()));
        planning.setEndTime(java.sql.Time.valueOf(node.findPath("planning").findPath("endTime").asText()));
        planning.setAvailable(Boolean.getBoolean(node.findPath("planning").get("available").asText()));

        Lesson lesson = new Lesson();
        if (node.findPath("planning").findPath("lesson").findPath("idLesson") != null){
            lesson.setIdLesson(node.findPath("planning").findPath("lesson").findPath("idLesson").asInt());
        }
        lesson.setName(node.findPath("planning").findPath("lesson").findPath("name").asText());
        lesson.setPrice(node.findPath("planning").findPath("lesson").findPath("price").asDouble());
        lesson.setDescription(node.findPath("planning").findPath("lesson").findPath("description").asText());
        Date pDate = new Date(node.findPath("planning").findPath("lesson").findPath("publicationDate").asLong());
        lesson.setPublicationDate(pDate);

        Subject subject = new Subject();
        subject.setMacroSubject(node.findPath("planning").findPath("lesson").findPath("subject").findPath("macroSubject").asText());
        subject.setMicroSubject(node.findPath("planning").findPath("lesson").findPath("subject").findPath("microSubject").asText());
        lesson.setSubject(subject);

        Teacher teacher = new Teacher();
        if (node.findPath("planning").findPath("lesson").findPath("teacher").findPath("idTeacher") != null) {
            teacher.setIdTeacher(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("idTeacher").asInt());
        }
        teacher.setPostCode(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("postCode").asInt());
        teacher.setCity(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("city").asText());
        teacher.setRegion(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("region").asText());
        teacher.setStreet(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("street").asText());
        teacher.setStreetNumber(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("streetNumber").asText());
        teacher.setByography(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("byography").asText());
        teacher.setIdUser(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("idUser").asInt());
        teacher.setEmail(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("email").asText());
        teacher.setName(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("name").asText());
        teacher.setSurname(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("surname").asText());
        Date birthdayTeacherDate = new Date(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("birthday").asLong());
        teacher.setBirthday(birthdayTeacherDate);
        teacher.setLanguage(Boolean.getBoolean(node.findPath("planning").findPath("lesson").findPath("teacher").get("language").asText()));
        if(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("image").asText().equals("null") || node.findPath("planning").findPath("lesson").findPath("teacher").findPath("image").asText().equals("")){
            teacher.setImage(null);
        } else teacher.setImage(node.findPath("planning").findPath("lesson").findPath("teacher").findPath("image").asText());

        lesson.setTeacher(teacher);

        planning.setLesson(lesson);

        booking.setPlanning(planning);

        return booking;
    }
}