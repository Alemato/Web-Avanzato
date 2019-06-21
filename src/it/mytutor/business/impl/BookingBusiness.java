package it.mytutor.business.impl;

import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import java.sql.Date;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllBooking;

public class BookingBusiness implements BookingInterface {
    //TODO Costruttore Booking

    @Override
    public List<Booking> findAllBooking() {

        return simulateFindAllBooking();
    }

    @Override
    public List<Booking> findAllBookingByDate(Date date) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByStudnet(Student student) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public Booking crateBooking(Booking booking) {
        return null;
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return null;
    }
}
