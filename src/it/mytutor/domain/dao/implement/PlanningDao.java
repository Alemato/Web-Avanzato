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
    private static final String ADD_PLANNING_STATEMENT = "insert into Planning (Date, StartTime, EndTime, IdLesson) values (?,?,?,?)";
    private static final String DELETE_PLANNING_STATEMENT = "delete from Planning where IdPlanning = ?";
    private static final String UPDATE_PLANNING_STATEMENT = "update Planning set Date=?, StartTime=?, EndTime=?, IdLesson=? where IdPlanning=?";
    private static final String GET_PLANNING_BY_FILTER_STATEMENT = "SELECT * from Planning p " +
            "join Lesson l on p.IdLesson = l.IdLesson " +
            "join Subject s on l.IdSubject = s.IdSubject " +
            "join Teacher t on l.IdTeacher = t.IdTeacher " +
            "join User u on t.IdUser = u.IdUser " +
            "and (0 = ? or s.MacroSubject =?)\n" +
            "and (0 = ? or l.Name = ?) and (0 = ? or t.City = ?) and (0 = ? or s.MicroSubject = ?) " +
            "and (0 = ? or DAYOFWEEK(p.Date) =? ) and (0 = ? or l.Price =? ) and (0 = ? or p.StartTime >= ? ) " +
            "and (0 = ? or p.EndTime <=? )";

    //TODO query per la ricerca nei
    // campi text. NB settare i campi sul db come indice FULTEXT
//    select *
//    from Message
//    where match(Text) AGAINST ('mondo');


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
            planning.setLesson(lesson);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Booking");
        }

    }

    private void configurelanningList(List<Planning> plannings, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {

                Planning planning = new Planning();
                Lesson lesson = new Lesson();
                Subject subject = new Subject();
                Teacher teacher = new Teacher();
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
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setString(1, planning.getDate().toString());
            prs.setString(2, planning.getStartTime().toString());
            prs.setString(3, planning.getEndTime().toString());
            prs.setInt(4, planning.getLesson().getIdLesson());
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void addPlanning(Planning planning) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(ADD_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setString(1, planning.getDate().toString());
            prs.setString(2, planning.getStartTime().toString());
            prs.setString(3, planning.getEndTime().toString());
            prs.setInt(4, planning.getLesson().getIdLesson());
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void deletePlanning(Planning planning) throws DatabaseException  {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(DELETE_PLANNING_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setInt(1, planning.getIdPlanning());
            prs.executeUpdate();
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
    public List<Planning> getPlanningByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant, String nome,
                                              int zonaRelevant, String zona, int microMateriaRelevant, String microMateria,
                                              int giornoSettimanaRelevant, String giornoSettimana, int prezzoRelevant,
                                              String prezzo, int oraInizioRelevant, String oraInizio,
                                              int oraFineaRelevant, String oraFine) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();
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
            configurelanningList(plannings, rs);
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
            prs.setString(1, planning.getDate().toString());
            prs.setString(2, planning.getStartTime().toString());
            prs.setString(3, planning.getEndTime().toString());
            prs.setInt(4, planning.getLesson().getIdLesson());
            prs.setInt(5, planning.getIdPlanning());

            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }
}
