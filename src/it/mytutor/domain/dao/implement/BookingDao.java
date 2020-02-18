package it.mytutor.domain.dao.implement;

import it.mytutor.domain.*;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookingDao implements BookingDaoInterface {
    private static final String CREATE_BOOKING_STATEMENT = "insert into Booking (Date, LessonState ,Idstudent, IdPlanning) values(?,?,?,?)";

    private static final String UPDATE_BOOKING_STATEMENT = "update Booking set Date = ?, LessonState = ?, IdStudent = ?, IdPlanning = ? where IdBooking = ?";

    private static final String GET_BOOKING_BY_ID_LESSON_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and l.IdLesson = ?";

    private static final String GET_ALL_BOOOKING_OF_A_STUDENT_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and st.IdStudent = ? ORDER BY p.Date ASC";

    private static final String GET_BOOKED_UP_BY_STUDENT_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and st.IdStudent = ? and (b.LessonState = 0)";

    private static final String GET_BOOKED_UP_BY_TEACHER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and t.IdTeacher = ? and (b.LessonState = 0)";

    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_STUDENT_AND_FILTER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (b.IdStudent = ?) and (0 = ? or s.MacroSubject = ?) and (0 = ? or match(l.Name) AGAINST(?)) and (0 = ? or s.MicroSubject = ?) and (0 = ? or b.Date = ?) and (0 = ? or t.IdUser = ?) and (0 = ? or b.LessonState = ?)";

    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_TEACHER_AND_FILTER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (l.IdTeacher = ?) and (0 = ? or s.MacroSubject = ?) and (0 = ? or match(l.Name) AGAINST(?)) and (0 = ? or s.MicroSubject = ?) and (0 = ? or b.Date = ?) and (0 = ? or st.IdUser = ?) and (0 = ? or b.LessonState = ?)";

    private static final String GET_ALL_BOOOKING_OF_A_TEACHER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and t.IdTeacher = ? ORDER BY p.Date ASC";

    private static final String GET_BOOKING_BY_ID_STATEMENT = "select * from Booking b, Student st, User u1, Planning p, Lesson l, Subject s, Teacher t, User u2 where b.IdBooking = ? and b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and  l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and  t.IdUser = u2.IdUser";

    private static final String GET_BOOKING_BY_FILTER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (0 = ? or s.MacroSubject = ?) " +
            "and (0 = ? or l.Name = ?) and (0 = ? or t.City = ?) and (0 = ? or s.MicroSubject = ?) " +
            "and (0 = ? or l.Price = ?) and (0 = ? or p.StartTime >= ?) " +
            "and (0 = ? or p.EndTime <= ?)";
    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_STUDENT_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (b.IdStudent = ?) and (b.LessonState = 2 or b.LessonState = 3 or b.LessonState = 4)";

    private static final String GET_ALL_HISTORICAL_BOOOKING_OF_A_TEACHER_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (b.IdStudent = ?) and (b.LessonState = 2 or b.LessonState = 3 or b.LessonState = 4)";
    private static final String GET_ALL_BOOKING_BOOKED_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and (b.LessonState = 2 or b.LessonState = 3) and p.Date < ?";

    private static final String GET_STUDENT_BY_ID_PLANNING_STATEMENT = "select * from Booking b, User u1, Student st, Planning p, Lesson l, User u2, Teacher t, Subject s where b.IdStudent = st.IdStudent and st.IdUser = u1.IdUser and b.IdPlanning = p.IdPlanning and p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u2.IdUser and l.IdSubject = s.IdSubject and p.IdPlanning = ?";

    private void configureBooking(Booking booking, Student student, Planning planning, Lesson lesson, Subject subject, Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {
            booking.setIdBooking(resultSet.getInt("b.IdBooking"));
            booking.setDate(resultSet.getDate("b.Date"));
            booking.setLessonState(resultSet.getInt("b.LessonState"));
            booking.setCreateDate(resultSet.getTimestamp("b.CreateDate"));
            booking.setUpdateDate(resultSet.getTimestamp("b.UpdateDate"));

            planning.setIdPlanning(resultSet.getInt("p.IdPlanning"));
            planning.setDate(resultSet.getDate("p.Date"));
            planning.setStartTime(resultSet.getTime("p.StartTime"));
            planning.setEndTime(resultSet.getTime("p.EndTime"));
            planning.setAvailable(resultSet.getBoolean("p.Available"));
            planning.setRepeatPlanning(resultSet.getBoolean("RepeatPlanning"));
            planning.setCreateDate(resultSet.getTimestamp("p.CreateDate"));
            planning.setUpdateDate(resultSet.getTimestamp("p.UpdateDate"));

            lesson.setIdLesson(resultSet.getInt("l.IdLesson"));
            lesson.setName(resultSet.getString("l.Name"));
            lesson.setPrice(resultSet.getDouble("l.Price"));
            lesson.setDescription(resultSet.getString("l.Description"));
            lesson.setPublicationDate(resultSet.getDate("l.PublicationDate"));
            lesson.setCreateDate(resultSet.getTimestamp("l.CreateDate"));
            lesson.setUpdateDate(resultSet.getTimestamp("l.UpdateDate"));

            subject.setIdSubject(resultSet.getInt("s.IdSubject"));
            subject.setMacroSubject(resultSet.getString("s.MacroSubject"));
            subject.setMicroSubject(resultSet.getString("s.MicroSubject"));
            subject.setCreateDate(resultSet.getTimestamp("s.CreateDate"));
            subject.setUpdateDate(resultSet.getTimestamp("s.UpdateDate"));
            lesson.setSubject(subject);

            teacher.setIdTeacher(resultSet.getInt("t.IdTeacher"));
            teacher.setPostCode(resultSet.getInt("t.PostCode"));
            teacher.setCity(resultSet.getString("t.City"));
            teacher.setRegion(resultSet.getString("t.Region"));
            teacher.setStreet(resultSet.getString("t.Street"));
            teacher.setStreetNumber(resultSet.getString("t.StreetNumber"));
            teacher.setByography(resultSet.getString("t.Byography"));
            teacher.setCrateDateTeacher(resultSet.getTimestamp("t.CreateDate"));
            teacher.setUpdateDateTeacher(resultSet.getTimestamp("t.UpdateDate"));
            teacher.setIdUser(resultSet.getInt("t.IdUser"));
            teacher.setEmail(resultSet.getString("u2.Email"));
            teacher.setRoles(resultSet.getInt("u2.Roles"));
            teacher.setPassword(resultSet.getString("u2.Password"));
            teacher.setName(resultSet.getString("u2.Name"));
            teacher.setSurname(resultSet.getString("u2.Surname"));
            teacher.setBirthday(resultSet.getDate("u2.Birthday"));
            teacher.setLanguage(resultSet.getBoolean("u2.Language"));
            teacher.setImage(resultSet.getString("u2.Image"));
            teacher.setCreateDate(resultSet.getTimestamp("u2.CreateDate"));
            teacher.setUpdateDate(resultSet.getTimestamp("u2.UpdateDate"));

            lesson.setTeacher(teacher);
            planning.setLesson(lesson);
            booking.setPlanning(planning);

            student.setIdStudent(resultSet.getInt("st.IdStudent"));
            student.setStudyGrade(resultSet.getString("st.StudyGrade"));
            student.setCreateDateStudent(resultSet.getTimestamp("st.CreateDate"));
            student.setUpdateDateStudent(resultSet.getTimestamp("st.UpdateDate"));
            student.setIdUser(resultSet.getInt("st.IdUser"));
            student.setEmail(resultSet.getString("u1.Email"));
            student.setRoles(resultSet.getInt("u1.Roles"));
            student.setPassword(resultSet.getString("u1.Password"));
            student.setName(resultSet.getString("u1.Name"));
            student.setSurname(resultSet.getString("u1.Surname"));
            student.setBirthday(resultSet.getDate("u1.Birthday"));
            student.setLanguage(resultSet.getBoolean("u1.Language"));
            student.setImage(resultSet.getString("u1.Image"));
            student.setCreateDate(resultSet.getTimestamp("u1.CreateDate"));
            student.setUpdateDate(resultSet.getTimestamp("u1.UpdateDate"));

            booking.setStudent(student);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Booking");
        }

    }

    private void configureBookingList(List<Booking> bookings, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Booking booking = new Booking();
                Student student = new Student();
                Planning planning = new Planning();
                Lesson lesson = new Lesson();
                Subject subject = new Subject();
                Teacher teacher = new Teacher();
                configureBooking(booking, student, planning, lesson, subject, teacher, resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Booking");
        }
    }


    @Override
    public int createBooking(Booking booking) throws DatabaseException {
        int id = -1;
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_BOOKING_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setDate(1, booking.getDate());
            prs.setInt(2, 0);
            prs.setInt(3, booking.getStudent().getIdStudent());
            prs.setInt(4, booking.getPlanning().getIdPlanning());
            prs.executeUpdate();

            rs = prs.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return id;
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
            prs.setDate(1, booking.getDate());
            prs.setInt(2, booking.getLessonState());
            prs.setInt(3, booking.getStudent().getIdStudent());
            prs.setInt(4, booking.getPlanning().getIdPlanning());
            prs.setInt(5, booking.getIdBooking());
            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public Booking findStudentByIdPlanning(Integer idPlanning)  throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        Booking booking = new Booking();
        Student student = new Student();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_STUDENT_BY_ID_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setInt(1, idPlanning);
            rs = prs.executeQuery();


            if (rs.next()) {
                configureBooking(booking, student, planning, lesson, subject, teacher, rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookingBooked(Date date) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_BOOKING_BOOKED_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setDate(1, date);
            rs = prs.executeQuery();
            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getAllBookingByIdLesson(Integer idLesson) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_BOOKING_BY_ID_LESSON_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idLesson);
            rs = prs.executeQuery();
            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllbookedUpByStudent(Student student) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_BOOKED_UP_BY_STUDENT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, student.getIdStudent());
            rs = prs.executeQuery();
            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllbookedUpByTeacher(Teacher teacher) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_BOOKED_UP_BY_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getIdTeacher());
            rs = prs.executeQuery();
            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant,
                                            String nome, int zonaRelevant, String zona, int microMateriaRelevant,
                                            String microMateria, int prezzoRelevant, String prezzo, int oraInizioRelevant,
                                            String oraInizio, int oraFineaRelevant, String oraFine) throws DatabaseException {

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

            prs.setInt(9, prezzoRelevant);
            prs.setString(10, prezzo);
            prs.setInt(11, oraInizioRelevant);
            prs.setString(12, oraInizio);
            prs.setInt(13, oraFineaRelevant);
            prs.setString(14, oraFine);

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
        Booking booking = new Booking();
        Student student = new Student();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_BOOKING_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setInt(1, id);
            rs = prs.executeQuery();


            if (rs.next()) {
                configureBooking(booking, student, planning, lesson, subject, teacher, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }

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
        List<Booking> bookings = new ArrayList<>();
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
            prs.setInt(1, student.getIdStudent());
            rs = prs.executeQuery();
            configureBookingList(bookings, rs);


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
        List<Booking> bookings = new ArrayList<>();
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
            prs.setInt(1, teacher.getIdTeacher());
            rs = prs.executeQuery();

            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getHistoricalBokingsOfAStudent(Student student) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
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
            rs = prs.executeQuery();

            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getHistoricalBokingsOfATeacher(Teacher teacher) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
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
            prs.setInt(1, teacher.getIdTeacher());
            rs = prs.executeQuery();

            configureBookingList(bookings, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getHistoricalBokingsOfAStudentAndFilter(Student student, int macroMateriaRevelant, String macroMateria, int nomeLezioneRevelant, String nomeLezione, int microMateriaRevelant, String microMateria, int dateRelevant, Date date, int idTeacherRelevant, int idProfessore, int statoRelevant, int stato) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_HISTORICAL_BOOOKING_OF_A_STUDENT_AND_FILTER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, student.getIdStudent());
            prs.setInt(2, macroMateriaRevelant);
            prs.setString(3, macroMateria);
            prs.setInt(4, nomeLezioneRevelant);
            prs.setString(5, nomeLezione);
            prs.setInt(6, microMateriaRevelant);
            prs.setString(7, microMateria);
            prs.setInt(8, dateRelevant);
            prs.setDate(9, date);
            prs.setInt(10, idTeacherRelevant);
            prs.setInt(11, idProfessore);
            prs.setInt(12, statoRelevant);
            prs.setInt(13, stato);
            rs = prs.executeQuery();

            configureBookingList(bookings, rs);


        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

    @Override
    public List<Booking> getHistoricalBokingsOfATeacherAndFilter(Teacher teacher, int macroMateriaRevelant, String macroMateria, int nomeLezioneRevelant, String nomeLezione, int microMateriaRevelant, String microMateria, int dateRelevant, Date date, int idStudentRelevant, int idStudent, int statoRelevant, int stato) throws DatabaseException {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_HISTORICAL_BOOOKING_OF_A_TEACHER_AND_FILTER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getIdTeacher());
            prs.setInt(2, macroMateriaRevelant);
            prs.setString(3, macroMateria);
            prs.setInt(4, nomeLezioneRevelant);
            prs.setString(5, nomeLezione);
            prs.setInt(6, microMateriaRevelant);
            prs.setString(7, microMateria);
            prs.setInt(8, dateRelevant);
            prs.setDate(9, date);
            prs.setInt(10, idStudentRelevant);
            prs.setInt(11, idStudent);
            prs.setInt(12, statoRelevant);
            prs.setInt(13, stato);
            rs = prs.executeQuery();

            configureBookingList(bookings, rs);


        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return bookings;
    }

}
