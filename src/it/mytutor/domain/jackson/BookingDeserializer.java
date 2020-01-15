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
        Student student = new Student();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
                
        if (node.get("idBooking") != null) {
            booking.setIdBooking(node.get("idBooking").asInt());
        }
        Date bookingDate = new Date(node.get("date").asLong());
        booking.setDate(bookingDate);
        booking.setLessonState(node.get("lessonState").asInt());
        
        JsonNode nodeStudent = node.get("student");
        if (nodeStudent.get("idStudent")!=  null) {
            student.setIdStudent(nodeStudent.get("idStudent").asInt());
        }
        student.setStudyGrade(nodeStudent.get("studyGrade").asText());
        student.setIdUser(nodeStudent.get("idUser").asInt());
        student.setEmail(nodeStudent.get("email").asText());
        student.setRoles(nodeStudent.get("roles").asInt());
        student.setPassword(nodeStudent.get("password").asText());
        student.setName(nodeStudent.get("name").asText());
        student.setSurname(nodeStudent.get("surname").asText());
        Date bDate = new Date(nodeStudent.get("birthday").asLong());
        student.setBirthday(bDate);
        student.setLanguage(Boolean.getBoolean(nodeStudent.get("language").asText()));
        if(nodeStudent.get("image").asText().equals("null")){
            student.setImage(null);
        } else student.setImage(nodeStudent.get("image").asText());
        booking.setStudent(student);

        JsonNode nodePlanning = node.get("planning");
        if (nodePlanning.get("idPlanning") != null) {
            planning.setIdPlanning(nodePlanning.get("idPlanning").asInt());
        }
        Date pDate = new Date(nodePlanning.get("date").asLong());
        student.setBirthday(pDate);
        planning.setStartTime(java.sql.Time.valueOf(nodePlanning.get("startTime").asText()));
        planning.setEndTime(java.sql.Time.valueOf(nodePlanning.get("endTime").asText()));

        JsonNode nodeLesson = nodePlanning.get("lesson");
        if (nodeLesson.get("idLesson") != null) {
            lesson.setIdLesson(nodeLesson.get("idLesson").asInt());
        }
        lesson.setName(nodeLesson.get("name").asText());
        lesson.setPrice(nodeLesson.get("price").asDouble());
        lesson.setDescription(nodeLesson.get("description").asText());
        lesson.setPublicationDate(new Date(nodeLesson.get("publicationDate").asLong()));
        
        JsonNode subjectNode = nodeLesson.get("subject");
        if (subjectNode.get("idSubject") != null) {
            subject.setIdSubject(subjectNode.get("idSubject").asInt());
        }
        subject.setMacroSubject(subjectNode.get("macroSubject").asText());
        subject.setMicroSubject(subjectNode.get("microSubject").asText());
        lesson.setSubject(subject);

        JsonNode nodeTeacher = nodePlanning.get("teacher");
        if (nodeTeacher.get("idTeacher") != null) {
            teacher.setIdTeacher(nodeTeacher.get("idTeacher").asInt());
        }
        teacher.setPostCode(nodeTeacher.get("postCode").asInt());
        teacher.setCity(nodeTeacher.get("city").asText());
        teacher.setRegion(nodeTeacher.get("region").asText());
        teacher.setStreet(nodeTeacher.get("street").asText());
        teacher.setStreetNumber(nodeTeacher.get("streetNumber").asText());
        teacher.setByography(nodeTeacher.get("byography").asText());
        teacher.setIdUser(nodeTeacher.get("idUser").asInt());
        teacher.setEmail(nodeTeacher.get("email").asText());
        teacher.setPassword(nodeTeacher.get("password").asText());
        teacher.setName(nodeTeacher.get("name").asText());
        teacher.setSurname(nodeTeacher.get("surname").asText());
        Date tDate = new Date(nodeTeacher.get("birthday").asLong());
        teacher.setBirthday(tDate);
        teacher.setLanguage(Boolean.getBoolean(nodeTeacher.get("language").asText()));
        if(nodeTeacher.get("image").asText().equals("null") || nodeTeacher.get("image").asText().equals("")){
            teacher.setImage(null);
        } else teacher.setImage(nodeTeacher.get("image").asText());
        lesson.setTeacher(teacher);
        planning.setLesson(lesson);
        booking.setPlanning(planning);
        

        return booking;
    }
}