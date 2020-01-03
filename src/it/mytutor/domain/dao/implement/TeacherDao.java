package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.TeacherDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao implements TeacherDaoInterface {
    private static final String CREATE_TEACHER_STATEMENT = "insert into Teacher(PostCode,City,Region,Street,StreetNumber,Byography,IdUser) values (?,?,?,?,?,?,?)";
    private static final String UPDATE_TEACHER_STATEMENT = "update Teacher set PostCode=?, City=?, Region=?, Street=?, StreetNumber=?, Byography=? where IdTeacher=?";
    private static final String GET_TEACHER_BY_ID_STATEMENT = "select * from Teacher t, User u where t.IdUser = u.IdUser AND t.IdTeacher=?";
    private static final String GET_TEACHER_BY_USER_ID = "select * from Teacher t, User u where t.IdUser = u.IdUser AND u.IdUser=?";
    private static final String GET_ALL_TEACHER_STATEMENT = "select * from Teacher t, User u where t.IdUser = u.IdUser";


    private void configureTeacher(Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {
            teacher.setIdTeacher(resultSet.getInt("IdTeacher"));
            teacher.setPostCode(resultSet.getInt("PostCode"));
            teacher.setCity(resultSet.getString("City"));
            teacher.setRegion(resultSet.getString("Region"));
            teacher.setStreet(resultSet.getString("Street"));
            teacher.setStreetNumber(resultSet.getString("StreetNumber"));
            teacher.setByography(resultSet.getString("Byography"));
            teacher.setCrateDateTeacher(resultSet.getTimestamp("t.CreateDate"));
            teacher.setUpdateDateTeacher(resultSet.getTimestamp("t.UpdateDate"));
            teacher.setIdUser(resultSet.getInt("t.IdUser"));
            teacher.setEmail(resultSet.getString("Email"));
            teacher.setRoles(resultSet.getInt("Roles"));
            teacher.setPassword(resultSet.getString("Password"));
            teacher.setName(resultSet.getString("Name"));
            teacher.setSurname(resultSet.getString("Surname"));
            teacher.setBirthday(resultSet.getDate("Birthday"));
            teacher.setLanguage(resultSet.getBoolean("Language"));
            teacher.setImage(resultSet.getString("Image"));
            teacher.setCreateDate(resultSet.getTimestamp("u.CreateDate"));
            teacher.setUpdateDate(resultSet.getTimestamp("u.UpdateDate"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Teacher");
        }
    }

    private void configureTeacherList(List<Teacher> teachers, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                configureTeacher(teacher, resultSet);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Teacher");
        }
    }

    @Override
    public void createTeacher(Teacher teacher) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getPostCode());
            prs.setString(2, teacher.getCity());
            prs.setString(3, teacher.getRegion());
            prs.setString(4, teacher.getStreet());
            prs.setString(5, teacher.getStreetNumber());
            prs.setString(6, teacher.getByography());
            prs.setInt(7, teacher.getIdUser());
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }


    }


    @Override
    public void modifyTeacher(Teacher teacher) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getPostCode());
            prs.setString(2, teacher.getCity());
            prs.setString(3, teacher.getRegion());
            prs.setString(4, teacher.getStreet());
            prs.setString(5, teacher.getStreetNumber());
            prs.setString(6, teacher.getByography());
            prs.setInt(7, teacher.getIdTeacher());
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }

    @Override
    public List<Teacher> getAllTeacher() throws DatabaseException {
        List<Teacher> teachers = new ArrayList<Teacher>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            rs = prs.executeQuery();
            configureTeacherList(teachers, rs);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return teachers;
    }

    @Override
    public Teacher getTeacherByUserID(int userId) throws DatabaseException {
        Teacher teacher = new Teacher();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_TEACHER_BY_USER_ID);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, userId);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureTeacher(teacher, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return teacher;
    }

    @Override
    public Teacher getTeacherByID(int id) throws DatabaseException {
        Teacher teacher = new Teacher();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_TEACHER_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, id);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureTeacher(teacher, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return teacher;
    }


}
