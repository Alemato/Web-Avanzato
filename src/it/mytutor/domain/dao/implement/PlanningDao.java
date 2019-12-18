package it.mytutor.domain.dao.implement;

import it.mytutor.domain.*;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanningDao implements PlanningDaoInterface {
    private static final String CREATE_PLANNING_STATEMENT = "insert into Planning (Date, StartTime, EndTime, IdLesson) values (?,?,?,?)";
    private static final String UPDATE_PLANNING_STATEMENT = "update into `Planning` set (Date=?,StartTime=?,EndTime=?,IdLesson=?) where id=?";
    private static final String GET_PLANNING_BY_FILTER_STATEMENT = "";
    private static final String GET_PLANNING_BY_STUDENT_STATEMENT = "";
    private static final String GET_PLANNING_BY_ID_STATEMENT = "";
    private static final String GET_ALL_PLANNING_OF_A_STATEMENT = "";

    private void configurePlanning(Planning planning, Lesson lesson, Subject subject, Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {
            planning.setIdPlanning(resultSet.getInt("p.IdPlanning"));
            planning.setDate(resultSet.getDate("p.Date"));
            planning.setStartTime(resultSet.getTime("p.StartTime"));
            planning.setEndTime(resultSet.getTime("p.EndTime"));
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
            teacher.setPostCode(resultSet.getInt("t.Postcode"));
            teacher.setCity(resultSet.getString("t.City"));
            teacher.setRegion(resultSet.getString("t.Region"));
            teacher.setStreet(resultSet.getString("t.Street"));
            teacher.setStreetNumber(resultSet.getString("t.StreetNumber"));
            teacher.setByography(resultSet.getString("t.Byography"));
            teacher.setCrateDateTeacher(resultSet.getTimestamp("t.CreateDate"));
            teacher.setUpdateDateTeacher(resultSet.getTimestamp("t.UpdateDate"));

            lesson.setTeacher(teacher);
            planning.setLesson(lesson);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Booking");
        }

    }

    private void configurelanningList(List<Planning> plannings, Lesson lesson, Subject subject, Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Planning planning = new Planning();
                configurePlanning(planning, lesson, subject, teacher, resultSet);
                plannings.add(planning);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Booking");
        }
    }


    @Override
    public void createPlanning(Planning planning) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs = conn.prepareStatement(CREATE_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setString(1, planning.getDate().toString());
            prs.setString(2, planning.getStartTime().toString());
            prs.setString(3, planning.getEndTime().toString());
            prs.setInt(4, planning.getLesson().getIdLesson());
            System.out.println(prs.toString());

//            prs.execute();
            System.out.println(prs.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public Planning getPlanningByID(int id) throws DatabaseException {
        return null;
    }

    @Override
    public Planning getPlanningByStudent(Student student) throws DatabaseException {
        return null;
    }

    @Override
    public List<Planning> getAllPlanningOfStudent(Student student) throws DatabaseException {
        return null;
    }

    @Override
    public List<Planning> getPlanningByFilter(int macroMateriaRelevant, String  macroMateria, int nomeRelevant, String nome,
                                              int zonaRelevant, String zona, int microMateriaRelevant, String microMateria,
                                              int giornoSettimanaRelevant, String giornoSettimana, int prezzoRelevant,
                                              String prezzo, int oraInizioRelevant, String oraInizio,
                                              int oraFineaRelevant, String oraFine) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();
        Lesson lesson = new Lesson();
        Subject subject = new Subject();
        Teacher teacher = new Teacher();
        Connection connection = DaoFactory.getConnection();
        if (connection == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(GET_PLANNING_BY_FILTER_STATEMENT);
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
            configurelanningList(plannings, lesson, subject, teacher, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(connection, rs, prs);
        }
        return plannings;
    }


    @Override
    public void updatePlanning(Planning planning) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setObject(1, planning.getDate());
            prs.setTime(2, planning.getStartTime());
            prs.setTime(3, planning.getEndTime());
            prs.setInt(4, planning.getLesson().getIdLesson());
            prs.setInt(5, planning.getIdPlanning());

            prs.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }
}
