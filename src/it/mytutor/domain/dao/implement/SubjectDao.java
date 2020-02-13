package it.mytutor.domain.dao.implement;

import com.mysql.jdbc.Statement;
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

    private static final String GET_STORICO_SUBJECT_STUDENT_STATEMENT = "select * from Subject s, Lesson l, Planning p, Booking b, Student st, User u where l.IdSubject = s.IdSubject and p.IdLesson = l.IdLesson and b.IdPlanning = p.IdPlanning and b.IdStudent = st.IdStudent and st.IdUser = u.IdUser and u.Email = ? group by s.IdSubject";

    private static final String GET_STORICO_SUBJECT_TEACHER_STATEMENT = "select * from Subject s, Lesson l, Planning p, Booking b, Teacher t, User u where l.IdSubject = s.IdSubject and p.IdLesson = l.IdLesson and b.IdPlanning = p.IdPlanning and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and u.Email = ? group by s.IdSubject";

    private static final String CREATE_SUBJECT_STATEMENT = "insert into Subject(macroSubject,microSubject) values(?,?)";

    private static final String GET_ALL_SUBJECT_STATEMENT = "select * from Subject order by MacroSubject";


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
    public Integer createSubject(Subject subject) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        int i = -1;
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_SUBJECT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, subject.getMacroSubject());
            prs.setString(2, subject.getMicroSubject());
            prs.executeUpdate();

            rs = prs.getGeneratedKeys();
            if (rs.next()) {
                i = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return i;
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
