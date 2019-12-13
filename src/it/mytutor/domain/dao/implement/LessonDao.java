package it.mytutor.domain.dao.implement;

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

    private static final String GET_LESSON_BY_TEACHER_STATEMENT = "select * from Lesson where IdTeacher = ?";

    private void configureLesson(Lesson lesson, ResultSet resultSet)throws DatabaseException {
        try {

        lesson.setIdLesson(resultSet.getInt(""));
        lesson.setName(resultSet.getString(""));
        lesson.setPrice(resultSet.getDouble(""));
        lesson.setDescription(resultSet.getString(""));
        lesson.setPublicationDate(resultSet.getDate(""));
        lesson.setCreateDate(resultSet.getTimestamp(""));
        lesson.setUpdateDate(resultSet.getTimestamp(""));
        Subject subject = new Subject();
        subject.setIdSubject(resultSet.getInt(""));
        lesson.setSubject(subject);
        Teacher teacher = new Teacher();
        teacher.setIdTeacher(resultSet.getInt(""));
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
                configureLesson(lesson, resultSet);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Lesson");
        }
    }

    @Override
    public List<Lesson> getAllLesson() throws DatabaseException {
        return null;
    }

    @Override
    public List<Lesson> getLessonByName(String name) throws DatabaseException {
        return null;
    }

    @Override
    public List<Lesson> getLessonsBySubject(String microSubject) throws DatabaseException {
        return null;
    }

    @Override
    public List<Lesson> getLessonsByTeacher(Teacher teacher) throws DatabaseException{
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
            prs.setInt(1,teacher.getIdTeacher());
            rs=prs.executeQuery();

            if (rs.next()){
                configureLessonList(lessons,rs);
            }else{
                throw new DatabaseException("rs is empty");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lessons;
    }

    @Override
    public Lesson getLessonsByID(int id) throws DatabaseException {
        return null;
    }

    @Override
    public void modifyLesson(Lesson lesson) throws DatabaseException {

    }

    @Override
    public void createLesson(Lesson lesson) throws DatabaseException {

    }

    /*private static final String GET_ALL_LESSON_STATEMENT="select * from Lesson";
    private static final String GET_LESSON_BY_ID_STATEMENT="select * from lesson where id=?";
    private static final String GET_LESSON_BY_SUBJECT_STATEMENT="select * from lesson where IdSubject=?";
    private static final String GET_LESSON_BY_NAME_STATEMENT="select * from Lesson where name=?";
    private static final String UPDATE_LESSON_STATEMENT="update Lesson set name=?,price=?,publicationDate=?,description=?,IdSubject=? where IdLesson=?";
    private static final String CREATE_LESSON_STATEMENT="insert into Lesson(name,price,publicationDate,description,IdSubject,IdTeacher) values(?,?,?,?,?,?)";

   private void configureLessonObject(Lesson lesson, ResultSet rs)throws DatabaseException {
       try {
           lesson.setIdLesson(rs.getInt("idLesson"));
           lesson.setName(rs.getString("name"));
           lesson.setPrice(rs.getDouble("price"));
           lesson.setPublicationDate(rs.getDate("publicationDate"));
           lesson.setDescription(rs.getString("description"));
           lesson.setSubject((Subject) rs.getObject("subject"));
           lesson.setCreateDate(rs.getTimestamp("createDate"));
           lesson.setUpdateDate(rs.getTimestamp("updateDate"));
       }catch(SQLException e){
           throw new DatabaseException(e.getMessage());
       }   }

    private void configureLessonList(List<Lesson> lessons, ResultSet rs) throws DatabaseException {
        try {
            while (rs.next()) {
                Lesson lesson = new Lesson();
                configureLessonObject(lesson, rs);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel creare Lista oggetti Lesson");
        }
    }


    @Override
    public void createLesson(Lesson lesson) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(CREATE_LESSON_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,lesson.getName());
            prs.setDouble(2,lesson.getPrice());
            prs.setDate(3,lesson.getPublicationDate());
            prs.setString(4,lesson.getDescription());
            prs.setInt(5,lesson.getSubject().getIdSubject());
            prs.setInt(6,lesson.getTeacher().getIdTeacher());
            prs.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public List<Lesson> getAllLesson()throws DatabaseException{
        List<Lesson> lessons=new ArrayList<Lesson>();

        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(GET_ALL_LESSON_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            rs=prs.executeQuery();
            if(rs.next()){
                configureLessonList(lessons,rs);
            }else{
                throw new DatabaseException("Statement is null");
            }
            }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lessons;
    }

    @Override
    public  Lesson getLessonsByID(int id) throws DatabaseException {
       Lesson lesson= new Lesson();
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(GET_LESSON_BY_ID_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,id);
            rs=prs.executeQuery();
            if(rs.next()){
                configureLessonObject(lesson,rs);
            }else{
                throw new DatabaseException("Statement is null");
            }
           }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lesson;
    }

    @Override
    public List<Lesson> getLessonByName (String name) throws DatabaseException{
        List<Lesson> lessons= new ArrayList<Lesson>();
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(GET_LESSON_BY_NAME_STATEMENT);
            prs.setString(1,name);
            rs=prs.executeQuery();
            if(rs.next()){
                configureLessonList(lessons,rs);
            }else{
                throw new DatabaseException("Statement is null");
            }
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }}catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return lessons;
    }

    @Override
    public List<Lesson> getLessonsBySubject(String microSubject) throws DatabaseException{
       List<Lesson> lessons=new ArrayList<Lesson>();
                Connection conn= DaoFactory.getConnection();
                if (conn==null){
                    throw new DatabaseException("Connection is null");
                }
                ResultSet rs=null;
                PreparedStatement prs=null;
                try {
                    prs=conn.prepareStatement(GET_LESSON_BY_SUBJECT_STATEMENT);
                    if(prs==null){
                        throw new DatabaseException("Statement is null");
                    }
                    prs.setString(1,microSubject);
                    rs=prs.executeQuery();
                    if(rs.next()){
                        configureLessonList(lessons,rs);
                    }else{
                        throw new DatabaseException("Statement is null");
                    }

                    }catch (SQLException e){
                    e.printStackTrace();
                    throw new DatabaseException(e.getMessage());
                }finally {
                    DaoFactory.closeDbConnection(conn, rs, prs);
                }
                return lessons;
    }

    @Override
    public void modifyLesson(Lesson lesson) throws DatabaseException{
                    Connection conn= DaoFactory.getConnection();
                    if (conn==null){
                        throw new DatabaseException("Connection is null");
                    }
                    ResultSet rs=null;
                    PreparedStatement prs=null;
                    try {
                        prs=conn.prepareStatement(UPDATE_LESSON_STATEMENT);
                        if(prs==null){
                            throw new DatabaseException("Statement is null");
                        }
                        prs.setString(1,lesson.getName());
                        prs.setDouble(2,lesson.getPrice());
                        prs.setDate(3,lesson.getPublicationDate());
                        prs.setString(4,lesson.getDescription());
                        prs.setObject(5,lesson.getSubject());
                        prs.setInt(6,lesson.getIdLesson());



                        prs.executeUpdate();

                    }catch (SQLException e){
                        e.printStackTrace();
                        throw new DatabaseException(e.getMessage());
                    }finally {
                        DaoFactory.closeDbConnection(conn, rs, prs);
                    }*/
    //}


}
