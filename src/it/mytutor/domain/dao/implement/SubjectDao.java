package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao implements SubjectDaoInterface {
    private static final String GET_SUBJECT_BY_ID_STATEMENT = "select * from Subject where IdSubject=?";
    private static final String GET_SUBJECTS_BY_NAME_STATEMENT = "select * from Subject where macroSubject=?";
    private static final String UPDATE_SUBJECT_STATEMENT = "update Subject set macroSubject=?, microSubject=? where idSubject=?";
    private static final String GET_STORICO_SUBJECT_STUDENT_STATEMENT = "select * from Subject s " +
            "join Lesson l on l.IdSubject = s.IdSubject " +
            "join Planning p on l.IdLesson = p.IdLesson " +
            "join Booking b on p.IdPlanning = b.IdPlanning " +
            "join Student st on b.IdStudent = st.IdStudent " +
            "join User u on st.IdStudent = u.IdUser " +
            "where u.Email = ? " +
            "group by s.IdSubject ";
    private static final String GET_STORICO_SUBJECT_TEACHER_STATEMENT = "select * from Subject s " +
            "join Lesson l on l.IdSubject = s.IdSubject " +
            "join Planning p on l.IdLesson = p.IdLesson " +
            "join Booking b on p.IdPlanning = b.IdPlanning " +
            "join Teacher t on l.IdTeacher = t.IdTeacher " +
            "join User u on t.IdUser = u.IdUser " +
            "where u.Email = ? " +
            "group by s.IdSubject ";
    private static final String CREATE_SUBJECT_STATEMENT = "insert into Subject(macroSubject,microSubject) values(?,?)";
    private static final String GET_ALL_SUBJECT_STATEMENT = "select * from Subject";


    private void configureSubject(Subject subject, ResultSet resultSet) throws DatabaseException {
        try {
            subject.setIdSubject(resultSet.getInt("IdSubject"));
            subject.setMacroSubject(resultSet.getString("MacroSubject"));
            subject.setMicroSubject(resultSet.getString("MicroSubject"));
            subject.setCreateDate(resultSet.getTimestamp("CreateDate"));
            subject.setUpdateDate(resultSet.getTimestamp("UpdateDate"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Subject");
        }
    }

    private void configureSubjectList(List<Subject> subjects, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Subject subject = new Subject();
                configureSubject(subject, resultSet);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Subject");
        }
    }

    @Override
    public Subject getSubjectById(int id) throws DatabaseException {
        Subject subject = new Subject();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_SUBJECT_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, id);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureSubject(subject, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return subject;
    }

    @Override
    public List<Subject> getSubjectsByName(String subjectName) throws DatabaseException {
        List<Subject> subjects = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_SUBJECTS_BY_NAME_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, subjectName);
            rs = prs.executeQuery();
            configureSubjectList(subjects, rs);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return subjects;
    }

    @Override
    public void createSubject(Subject subject) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_SUBJECT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, subject.getMacroSubject());
            prs.setString(2, subject.getMicroSubject());
            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void modifySubjectByID(Subject subject) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_SUBJECT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, subject.getMacroSubject());
            prs.setString(2, subject.getMicroSubject());
            prs.setInt(3, subject.getIdSubject());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public List<Subject> getAllSubject() throws DatabaseException {
        List<Subject> subjects = new ArrayList<Subject>();

        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_SUBJECT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            rs = prs.executeQuery();
            configureSubjectList(subjects, rs);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return subjects;
    }

    @Override
    public List<Subject> getStoricoSubjectStudent(String email) throws DatabaseException {
        List<Subject> Subejcts = new ArrayList<Subject>();

        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_STORICO_SUBJECT_STUDENT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, email);
            rs = prs.executeQuery();
            configureSubjectList(Subejcts, rs);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return Subejcts;
    }

    @Override
    public List<Subject> getStoricoSubjectTeacher(String email) throws DatabaseException {
        List<Subject> Subejcts = new ArrayList<Subject>();

        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_STORICO_SUBJECT_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, email);
            rs = prs.executeQuery();
            configureSubjectList(Subejcts, rs);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return Subejcts;
    }

}
