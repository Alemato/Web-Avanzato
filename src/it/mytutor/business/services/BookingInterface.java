package it.mytutor.business.services;

import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public interface BookingInterface {
    List<Booking> findAllBooking();
    List<Booking> findBookingByFilter(String macroMateria, String nome, String zona, String microMateria,
                                      String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException;
    Booking findBookingById(Integer idBooking);
    List<Booking> findAllBookingByLessonAndDate(Date date, Lesson lesson);
    List<Booking> findAllBookingByDate(Date date);
    List<Booking> findAllBookingByStudnet(Student student) throws DatabaseException;
    List<Booking> findAllBookingByTeacher(Teacher teacher) throws DatabaseException;
    List<Booking> findAllBookingByLesson(Lesson lesson);
    Booking crateBooking(Booking booking);
    Booking updateBooking(Booking booking, Integer lessonState);
    List<Booking> findHistoricalBookingByStudent(Student student, String nomeLezione, String macroMateria,
                                                 String microMateria, String idTeacher, String date,
                                                 String rifiutata, String annullata, String eseguita) throws DatabaseException, ParseException;
    List<Booking> findHistoricalBookingByTeacher(Teacher teacher, String nomeLezione, String macroMateria,
                                                 String microMateria, String idTeacher, String date,
                                                 String rifiutata, String annullata, String eseguita) throws DatabaseException;
}
