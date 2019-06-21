package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface BookingDaoInterface {

    void createBooking(Booking booking)throws DatabaseException;

    void updateBooking(Booking booking)throws DatabaseException;



    Booking getBookingById(int id)throws DatabaseException;

    List<Booking> getAllBookingOfAStudent(Student student)throws DatabaseException;

}
