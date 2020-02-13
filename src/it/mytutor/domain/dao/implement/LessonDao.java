package it.mytutor.domain.dao.implement;

import com.mysql.jdbc.Statement;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.LessonDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao implements LessonDaoInterface {

    private static final String GET_LESSON_BY_TEACHER_STATEMENT = "select * from Lesson l, Teacher t, User u, Subject s where l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and l.IdSubject = s.IdSubject and l.IdTeacher = ?";

    private static final String CREATE_LESSON_STATEMENT = "insert into Lesson (Name, Price, Description, IdSubject, IdTeacher) values (?, ?, ?, ?, ?)";

    private static final String UPDATE_LESSON_STATEMENT = "update Lesson set name=?, price=?, description=?, IdSubject=? where IdLesson=?";

    private static final String GET_LESSON_BY_ID_STATEMENT =  "select * from Lesson l, Teacher t, User u, Subject s where l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and l.IdSubject = s.IdSubject and l.IdLesson = ?";

    private void configureLesson(Lesson lesson, Subject subject, Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {

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
            teacher.setPostCode(resultSet.getInt("t.Postcode"));
            teacher.setCity(resultSet.getString("t.City"));
            teacher.setRegion(resultSet.getString("t.Region"));
            teacher.setStreet(resultSet.getString("t.Street"));
            teacher.setStreetNumber(resultSet.getString("t.StreetNumber"));
            teacher.setByography(resultSet.getString("t.Byography"));
            teacher.setCrateDateTeacher(resultSet.getTimestamp("t.CreateDate"));
            teacher.setUpdateDateTeacher(resultSet.getTimestamp("t.UpdateDate"));

            teacher.setIdUser(resultSet.getInt("u.IdUser"));
            teacher.setEmail(resultSet.getString("u.Email"));
            teacher.setRoles(resultSet.getInt("u.Roles"));
            teacher.setPassword(resultSet.getString("u.Password"));
            teacher.setName(resultSet.getString("u.Name"));
            teacher.setSurname(resultSet.getString("u.Surname"));
            teacher.setBirthday(resultSet.getDate("u.Birthday"));
            teacher.setLanguage(resultSet.getBoolean("u.Language"));
            teacher.setImage(resultSet.getString("u.Image"));
            teacher.setCreateDate(resultSet.getTimestamp("u.CreateDate"));
            teacher.setUpdateDate(resultSet.getTimestamp("u.UpdateDate"));
            lesson.setTeacher(teacher);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Lesson");
        }
    }

    private void configureLessonList(List<Lesson> lessons, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                Subject subject = new Subject();
                Teacher teacher = new Teacher();
                configureLesson(lesson, subject, teacher, resultSet);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Lesson");
        }
    }

    @Override
    public List<Lesson> getLessonsByTeacher(Teacher teacher) throws DatabaseException {
        List<Lesson> lessons = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_LESSON_BY_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getIdTeacher());
            rs = prs.executeQuery();

            configureLessonList(lessons, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lessons;
    }

    @Override
    public int createLesson(Lesson lesson) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        int i = -1;
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_LESSON_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setString(1, lesson.getName());
            prs.setString(2, lesson.getPrice().toString());
            prs.setString(3, lesson.getDescription());
            prs.setInt(4, lesson.getSubject().getIdSubject());
            prs.setInt(5, lesson.getTeacher().getIdTeacher());

            prs.executeUpdate();

            rs = prs.getGeneratedKeys();
            if (rs.next()) {
                i = rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }

        finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return i;

    }

    @Override
    public void modifyLesson(Lesson lesson) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_LESSON_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, lesson.getName());
            prs.setDouble(2, lesson.getPrice());
            prs.setString(3, lesson.getDescription());
            prs.setInt(4, lesson.getSubject().getIdSubject());
            prs.setInt(5, lesson.getIdLesson());
            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public Lesson getLessonById(Integer idLesson) throws DatabaseException {
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_LESSON_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idLesson);
            rs = prs.executeQuery();

            if (rs.next()) {
                configureLesson(lesson, subject,teacher, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lesson;
    }
}
