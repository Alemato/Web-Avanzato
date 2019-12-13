package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Booking;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class BookingDao  implements BookingDaoInterface {
    private static final String CREATE_BOOKING_STATEMENT="insert into Booking(Date,LessonState,Idstudent,IdPlanning) values(?,?,?,?,?)";
    private static final String UPDATE_BOOKING_STATEMENT="update Booking set Date=?,LessonState=?,IdStudent=?,IdPlanning=? where IdBooking=?";
    private static final String GET_ALL_BOOOKING_OF_A_STUDENT_STATEMENT="select * from Booking where IdStudent = ? and (LessonState = 0 or LessonState = 1)";
    private static final String GET_ALL_BOOOKING_OF_A_TEACHER_STATEMENT="select * from Booking b " +
            "join Planning p on p.IdPlanning = b.IdPlanning " +
            "join Lesson l on l.IdLesson = p.IdLesson join Teacher t on l.IdTeacher = t.IdTeacher " +
            "where t.IdTeacher = ? and (LessonState = 0 or LessonState = 1)";
    private static final String GET_BOOKING_BY_ID_STATEMENT="select * from Booking where IdBooking=?";

    private static final String GET_BOOKING_BY_FILTER_STATEMENT = "select * from Booking b " +
            "join Planning p on p.IdPlanning = b.IdPlanning " +
            "join Lesson l on p.IdLesson = l.idLesson " +
            "join Subject s on s.idSubject = l.idSubject " +
            "join Teacher t on t.IdTeacher = l.IdTeacher where (0 = ? or s.MacroSubject = ?) " +
            "and (0 = ? or l.Name = ?) and (0 = ? or t.City = ?) and (0 = ? or s.MicroSubject = ?) " +
            "and (0 = ? or DAYOFWEEK(p.Date) = ?) and (0 = ? or l.Price = ?) and (0 = ? or p.StartTime >= ?) " +
            "and (0 = ? or p.EndTime <= ?)";
    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_STUDENT_STATEMENT = "select * from Booking b join Planning p on b.IdPlanning = p.IdPlanning join Lesson l on p.IdLesson = l.IdLesson join Subject s on l.IdSubject = s.IdSubject where (b.IdStudent = ?) and (0 = ? or l.Name = ?) and (0 = ? or s.MacroSubject = ?) and (0 = ? or s.MicroSubject = ?) and (0 = ? or l.IdTeacher = ?) and (0 = ? or p.Date = ?) and (0 = ? or b.LessonState = 2) and (0 = ? or b.LessonState = 3) and (0 = ? or b.LessonState = 4)";
    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_TEACHER_STATEMENT = "select * from Booking b join Planning p on b.IdPlanning = p.IdPlanning join Lesson l on p.IdLesson = l.IdLesson join Subject s on l.IdSubject = s.IdSubject where (l.IdTeacher = ?) and (0 = ? or l.Name = ?) and (0 = ? or s.MacroSubject = ?) and (0 = ? or s.MicroSubject = ?) and (0 = ? or IdStudent = ?) and (0 = ? or p.Date = ?) and (0 = ? or b.LessonState = 2) and (0 = ? or b.LessonState = 3) and (0 = ? or b.LessonState = 4)";


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
        prs.setInt(4,booking.getPlanning().getIdPlanning());
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
            prs.setInt(4,booking.getPlanning().getIdPlanning());
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
    public List<Booking> getBookingByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant,
                                            String nome, int zonaRelevant, String zona, int microMateriaRelevant,
                                            String microMateria, int giornoSettimanaRelevant, String giornoSettimana,
                                            int prezzoRelevant, String prezzo, int oraInizioRelevant,
                                            String oraInizio, int oraFineaRelevant, String oraFine) throws DatabaseException  {

        List<Booking> bookings = new ArrayList<>();
        Connection connection = DaoFactory.getConnection();
        if (connection == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(GET_BOOKING_BY_FILTER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, macroMateriaRelevant);
            prs.setString(2, macroMateria);
            prs.setInt(3, nomeRelevant);
            prs.setString(4, nome);
            prs.setInt(5, zonaRelevant);
            prs.setString(6, zona);
            prs.setInt(7, microMateriaRelevant);
            prs.setString(8, microMateria);
            prs.setInt(9, giornoSettimanaRelevant);
            prs.setString(10, giornoSettimana);
            prs.setInt(11, prezzoRelevant);
            prs.setString(12, prezzo);
            prs.setInt(13, oraInizioRelevant);
            prs.setString(14, oraInizio);
            prs.setInt(15, oraFineaRelevant);
            prs.setString(16, oraFine);

            rs = prs.executeQuery();
            configureBookingList(bookings, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(connection, rs, prs);
        }
        return bookings;
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

    @Override
    public List<Booking> getAllBookingOfATeacher(Teacher teacher) throws DatabaseException {
        List<Booking> bookings= new ArrayList<Booking>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_BOOOKING_OF_A_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,teacher.getIdTeacher());
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

    @Override
    public List<Booking> getHistoricalBokingsOfAStudent(Student student, int nomeLezioneRevelant, String nomeLezione, int macroMateriaRevelant, String macroMateria, int microMateriaRevelant, String microMateria, int idTeacherRevelant, int idTeacher, int dateRevelant, Date date, int rifiutataRevelant, String rifiutata, int annullataRevelant, String annullata, int eseguitaRevelant, String eseguita) throws DatabaseException {
        List<Booking> bookings= new ArrayList<Booking>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_HISTORICAL_BOOOKING_OF_A_STUDENT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, student.getIdStudent());
            prs.setInt(2, nomeLezioneRevelant);
            prs.setString(3, nomeLezione);
            prs.setInt(4, macroMateriaRevelant);
            prs.setString(5, macroMateria);
            prs.setInt(6, microMateriaRevelant);
            prs.setString(7, microMateria);
            prs.setInt(8, idTeacherRevelant);
            prs.setInt(9, idTeacher);
            prs.setInt(10, dateRevelant);
            prs.setDate(11, date);
            prs.setInt(12, rifiutataRevelant);
            prs.setInt(13, annullataRevelant);
            prs.setInt(14, eseguitaRevelant);
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

    @Override
    public List<Booking> getHistoricalBokingsOfATeacher(Teacher teacher, int nomeLezioneRevelant, String nomeLezione, int macroMateriaRevelant, String macroMateria, int microMateriaRevelant, String microMateria, int idTeacherRevelant, int idStudent, int dateRevelant, Date date, int rifiutataRevelant, String rifiutata, int annullataRevelant, String annullata, int eseguitaRevelant, String eseguita) throws DatabaseException {
        List<Booking> bookings= new ArrayList<Booking>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_HISTORICAL_BOOOKING_OF_A_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,teacher.getIdTeacher());
            prs.setInt(2, nomeLezioneRevelant);
            prs.setString(3, nomeLezione);
            prs.setInt(4, macroMateriaRevelant);
            prs.setString(5, macroMateria);
            prs.setInt(6, microMateriaRevelant);
            prs.setString(7, microMateria);
            prs.setInt(8, idTeacherRevelant);
            prs.setInt(9, idStudent);
            prs.setInt(10, dateRevelant);
            prs.setDate(11, date);
            prs.setInt(12, rifiutataRevelant);
            prs.setInt(13, annullataRevelant);
            prs.setInt(14, eseguitaRevelant);
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
