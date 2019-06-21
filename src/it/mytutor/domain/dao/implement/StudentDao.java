package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Student;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.StudentDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements StudentDaoInterface {
    private static final String CREATE_STUDENT_STATEMENT="insert into Student(studyGrade,IdUser) value (?,?)";
    private static final String UPDATE_STUDENT_STATEMENT="update Student set studyGrade=? where idStudent=?";
    private static final String GET_STUDENT_BY_ID_STATEMENT="select * from Student where idStudent=?";
    private static final String GET_ALL_STUDENT_STATEMENT="select * from Student";

   private void configureStudent(Student student, ResultSet resultSet) throws DatabaseException {
        try {

            student.setIdStudent(resultSet.getInt("IdStudent"));
            student.setStudyGrade(resultSet.getString("studyGrade"));
            student.setIdUser(resultSet.getInt("IdUser"));
            student.setCreateDate(resultSet.getTimestamp("createDateStudent"));
            student.setUpdateDate(resultSet.getTimestamp("updateDateStudent"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Student");
        }
    }

    private void configureStudentList(List<Student> students, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Student student= new Student();
                configureStudent(student, resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Student");
        }
    }


    @Override
    public void createStudent(Student student) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(CREATE_STUDENT_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,student.getStudyGrade());
            prs.setInt(2,student.getIdUser());

            prs.executeQuery();

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }

    }

    @Override
    public void modifyStudent(Student student) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(UPDATE_STUDENT_STATEMENT);
            if(prs==null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,student.getStudyGrade());
            prs.executeUpdate();

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }

    }



    @Override
    public List<Student> getAllStudent() throws DatabaseException {
        List<Student> students=new ArrayList<Student>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_STUDENT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            rs=prs.executeQuery();
            if(rs.next()){
                configureStudentList(students,rs);
            }else{
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return students;
    }

    @Override
    public Student getStudentByID(int id) throws DatabaseException {
        Student student=new Student();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_STUDENT_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,id);
            rs=prs.executeQuery();
            if(rs.next()){
                configureStudent(student,rs);
            }else{
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return student;
    }
}
