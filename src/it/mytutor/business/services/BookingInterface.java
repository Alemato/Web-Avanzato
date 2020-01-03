package it.mytutor.business.services;

import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.UserException;
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
                                      String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException, PlanningBusinessException, BookingBusinessException, UserException;
    Booking findBookingById(Integer idBooking) throws BookingBusinessException;
    List<Booking> findAllBookingByLessonAndDate(Date date, Lesson lesson);
    List<Booking> findAllBookingByDate(Date date);
    List<Booking> findAllBookingByStudnet(Student student) throws DatabaseException, PlanningBusinessException, BookingBusinessException, UserException;
    List<Booking> findAllBookingByTeacher(Teacher teacher) throws DatabaseException, PlanningBusinessException, BookingBusinessException, UserException;
    List<Booking> findAllBookingByLesson(Lesson lesson);
    void crateBookings(List<Booking> bookings) throws PlanningBusinessException;
    void updateBooking(Booking booking, Integer lessonState) throws BookingBusinessException;
    List<Booking> findHistoricalBookingByStudent(Student student) throws DatabaseException, ParseException, PlanningBusinessException, BookingBusinessException, UserException;
    List<Booking> findHistoricalBookingByTeacher(Teacher teacher) throws Exception;

    List<Booking> findAllBookingByStudnetAndFilter(Student student, String macroMateria, String nomeLezione, String microMateria, String date, String idProfessore, String stato) throws ParseException, UserException;

    List<Booking> findAllBookingByTeacherAndFilter(Teacher teacher, String macroMateria, String nomeLezione, String microMateria, String date, String idStudente, String stato) throws ParseException, UserException;
}
