package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Booking;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class BookingDao  implements BookingDaoInterface {
    private static final String CREATE_BOOKING_STATEMENT="insert into Booking(Date,LessonState,Idstudent,IdLesson) values(?,?,?,?,?)";
    private static final String UPDATE_BOOKING_STATEMENT="update Booking set Date=?,LessonState=?,IdStudent=?,IdLesson=? where IdBooking=?";
    private static final String GET_ALL_BOOOKING_OF_A_STUDENT_STATEMENT="select * from Booking where IdStudent=? ";
    private static final String GET_BOOKING_BY_ID_STATEMENT="select * from Booking where IdBooking=?";


    private void configureBooking(Booking booking, ResultSet resultSet) throws DatabaseException {
      //da configurare l'oggetto student e l'oggetto lesson all'interno di booking
        try {
            booking.setIdBooking(resultSet.getInt("idBooking"));
            booking.setDate(resultSet.getDate("Date"));
            booking.setLessonState(resultSet.getInt("LessonState"));
            booking.setCreateDate(resultSet.getTimestamp("createDate"));
            booking.setUpdateDate(resultSet.getTimestamp("updateDate"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Booking");
        }

    }
    private void configureBookingList(List<Booking> bookings, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Booking booking = new Booking();
                configureBooking(booking, resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Booking");
        }
    }




    @Override
    public void createBooking(Booking booking) throws DatabaseException {

        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_BOOKING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
        prs.setDate(1,booking.getDate());
        prs.setInt(2,booking.getLessonState());
        prs.setInt(3,booking.getStudent().getIdStudent());
        prs.setInt(4,booking.getLesson().getIdLesson());
        prs.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void updateBooking(Booking booking) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_BOOKING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setDate(1,booking.getDate());
            prs.setInt(2,booking.getLessonState());
            prs.setInt(3,booking.getStudent().getIdStudent());
            prs.setInt(4,booking.getLesson().getIdLesson());
            prs.setInt(5,booking.getIdBooking());
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }


    @Override
    public Booking getBookingById(int id) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        Booking booking=new Booking();
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_BOOKING_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

        prs.setInt(1,id);
        rs=prs.executeQuery();


            if (rs.next()){
                configureBooking(booking,rs);
            }else{throw new DatabaseException("rs is empty");}

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookingOfAStudent(Student student) throws DatabaseException {
        List<Booking> bookings= new ArrayList<Booking>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_BOOOKING_OF_A_STUDENT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,student.getIdStudent());
            rs=prs.executeQuery();

            if (rs.next()){
                configureBookingList(bookings,rs);
            }else{
                throw new DatabaseException("rs is empty");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

}
