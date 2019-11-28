package it.mytutor.business.services;

import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import java.sql.Date;
import java.util.List;

public interface BookingInterface {
    List<Booking> findAllBooking();
    Booking findBookingById(Integer idBooking);
    List<Booking> findAllBookingByLessonAndDate(Date date, Lesson lesson);
    List<Booking> findAllBookingByDate(Date date);
    List<Booking> findAllBookingByStudnet(Student student);
    List<Booking> findAllBookingByTeacher(Teacher teacher);
    List<Booking> findAllBookingByLesson(Lesson lesson);
    Booking crateBooking(Booking booking);
    Booking updateBooking(Booking booking, String status);
}
