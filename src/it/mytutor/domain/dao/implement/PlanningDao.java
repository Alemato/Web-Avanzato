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
    private static final String CREATE_PLANNING_STATEMENT = "insert into Planning (Date, StartTime, EndTime, Available, RepeatPlanning, IdLesson) values (?,?,?,?,?,?)";

    private static final String ADD_PLANNING_STATEMENT = "insert into Planning (Date, StartTime, EndTime, Available, RepeatPlanning, IdLesson) values (?,?,?,?,?,?)";

    private static final String DELETE_PLANNING_BY_ID_LESSON_STATEMENT = "delete from Planning where IdLesson = ? and Available = true";

    private static final String DELETE_PLANNING_STATEMENT = "delete from Planning where IdPlanning between ? and ?";

    private static final String UPDATE_PLANNING_STATEMENT = "update Planning set Date=?, StartTime=?, EndTime=?, Available=?, RepeatPlanning =? , IdLesson=? where IdPlanning=?";

    private static final String GET_PLANNING_BY_FILTER_STATEMENT = "SELECT * from Planning p, Lesson l, Subject s, Teacher t, User u where p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and (0 = ? or s.MacroSubject = ?) and (0 = ? or match(l.Name) AGAINST (?)) and (0 = ? or t.City = ?) and (0 = ? or s.MicroSubject = ?) and (0 = ? or l.Price =? ) and (0 = ? or p.StartTime >= ? ) and (0 = ? or p.EndTime <= ?) and p.Date >= NOW() and p.Date <= DATE_ADD(NOW(), INTERVAL 60 DAY) ORDER BY p.IdLesson, p.Date, p.StartTime ASC";

    private static final String GET_PLANNING_BY_LESSON_ID_STATEMENT = "SELECT p.*, l.*, s.*, t.*, u.IdUser, u.Email, u.Roles, u.Name, u.Surname, u.Password, u.Birthday, u.Language, u.CreateDate, u.UpdateDate from Planning p, Lesson l, Subject s, Teacher t, User u where p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and p.IdLesson = ? and p.Available = true order by p.Date, p.StartTime asc";

    private static final String GET_PLANNING_BOOKED_UP_BY_LESSONID_STATEMENT = "SELECT * from Planning p, Lesson l, Subject s, Teacher t, User u where p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and p.IdLesson = ? and b.IdStudent = ? order by p.CreateDate";

    private static final String GET_PLANNING_BY_TEACHER_STATEMENT = "select * from planning p, Lesson l, teacher t, user u, subject s where p.IdLesson = l.IdLesson and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and l.IdSubject = s.IdSubject and t.IdTeacher = ?";

    private static final String GET_PLANNING_BY_ID_STATEMENT = "SELECT * from Planning p, Lesson l, Subject s, Teacher t, User u where p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and p.IdPlanning = ?";

    private static final String GET_FULL_PLANNING_BY_FILTER = "SELECT * FROM Planning p, Lesson l, Subject s, Teacher t, User u WHERE p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and p.Available = true and p.Date >= NOW() and p.Date <= DATE_ADD(NOW(), INTERVAL 60 DAY) and p.IdLesson IN (SELECT l.IdLesson from Planning p, Lesson l, Subject s, Teacher t, User u where p.IdLesson = l.IdLesson and l.IdSubject = s.IdSubject and l.IdTeacher = t.IdTeacher and t.IdUser = u.IdUser and p.Available = true and  (0 = ? or s.MacroSubject = ?) and (0 = ? or match(l.Name) AGAINST (?)) and (0 = ? or t.City = ?) and (0 = ? or s.MicroSubject = ?) and (0 = ? or l.Price =? ) and (0 = ? or p.StartTime >= ? ) and (0 = ? or p.EndTime <= ?) and ((1 = ? and DAYOFWEEK(p.Date) = 1) or (1 = ? and DAYOFWEEK(p.Date) = 2) or (1 = ? and DAYOFWEEK(p.Date) = 3) or (1 = ? and DAYOFWEEK(p.Date) = 4) or (1 = ? and DAYOFWEEK(p.Date) = 5) or (1 = ? and DAYOFWEEK(p.Date) = 6) or (1 = ? and DAYOFWEEK(p.Date) = 7) ) and p.Date >= NOW() and p.Date <= DATE_ADD(NOW(), INTERVAL 60 DAY) group BY p.IdLesson) ORDER BY p.IdLesson, p.Date, p.StartTime";

    private void configurePlanning(Planning planning, Lesson lesson, Subject subject, Teacher teacher, ResultSet resultSet) throws DatabaseException {
        try {
            planning.setIdPlanning(resultSet.getInt("p.IdPlanning"));
            planning.setDate(resultSet.getDate("p.Date"));
            planning.setStartTime(resultSet.getTime("p.StartTime"));
            planning.setEndTime(resultSet.getTime("p.EndTime"));
            planning.setAvailable(resultSet.getBoolean("p.Available"));
            planning.setRepeatPlanning(resultSet.getBoolean("p.RepeatPlanning"));
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
            try {
                teacher.setImage(resultSet.getString("u.Image"));
            } catch (SQLException sqlex) {
                teacher.setImage(null);
            }
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
            prs.setBoolean(4, planning.getAvailable());
            prs.setBoolean(5, planning.getRepeatPlanning());
            prs.setInt(6, planning.getLesson().getIdLesson());
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
            prs.setBoolean(4, true);
            prs.setBoolean(5, planning.getRepeatPlanning());
            prs.setInt(6, planning.getLesson().getIdLesson());
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void deletePlanningsByLesson(Integer idLesson) throws DatabaseException  {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(DELETE_PLANNING_BY_ID_LESSON_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }

            prs.setInt(1, idLesson);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }


    @Override
    public void deletePlanning(Integer idFirst, Integer idLast) throws DatabaseException  {
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

            prs.setInt(1, idFirst);
            prs.setInt(2, idLast);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public List<Planning> getFullPlanningByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant, String nome,
                                                  int zonaRelevant, String zona, int microMateriaRelevant, String microMateria,
                                                  int prezzoRelevant, String prezzo, int oraInizioRelevant, String oraInizio,
                                                  int oraFineRelevant, String oraFine, int dom, int lun, int mar, int mer, int gio, int ven, int sab) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;

        try {
            prs = conn.prepareStatement(GET_FULL_PLANNING_BY_FILTER);
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
            prs.setInt(13, oraFineRelevant);
            prs.setString(14, oraFine);
            prs.setInt(15, dom);
            prs.setInt(16, lun);
            prs.setInt(17, mar);
            prs.setInt(18, mer);
            prs.setInt(19, gio);
            prs.setInt(20, ven);
            prs.setInt(21, sab);

            rs = prs.executeQuery();
            configurelanningList(plannings, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return plannings;
    }

    public Planning getPlanningById(Integer idPlanning) throws DatabaseException {
        Planning planning = new Planning();
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
            prs = conn.prepareStatement(GET_PLANNING_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idPlanning);
            rs = prs.executeQuery();

            if (rs.next()) {
                configurePlanning(planning, lesson, subject, teacher, rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return planning;
    }

    @Override
    public List<Planning> getPlanningByTeacher(Teacher teacher) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();
        Connection connection = DaoFactory.getConnection();
        if (connection == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(GET_PLANNING_BY_TEACHER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, teacher.getIdTeacher());

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
    public List<Planning> getPlanningByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant, String nome,
                                              int zonaRelevant, String zona, int microMateriaRelevant, String microMateria,
                                              int prezzoRelevant, String prezzo, int oraInizioRelevant, String oraInizio,
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
            prs.setInt(9, prezzoRelevant);
            prs.setString(10, prezzo);
            prs.setInt(11, oraInizioRelevant);
            prs.setString(12, oraInizio);
            prs.setInt(13, oraFineaRelevant);
            prs.setString(14, oraFine);

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
    public List<Planning> getPlanningByLessonId(Integer idLesson) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();

        Connection connection = DaoFactory.getConnection();
        if (connection == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(GET_PLANNING_BY_LESSON_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idLesson);

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
    public List<Planning> getPlanningBookedUpByLessonId(Integer idLesson, Integer idStudent) throws DatabaseException {
        List<Planning> plannings = new ArrayList<>();

        Connection connection = DaoFactory.getConnection();
        if (connection == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(GET_PLANNING_BOOKED_UP_BY_LESSONID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idLesson);
            prs.setInt(2, idStudent);

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
            prs.setBoolean(4, planning.getAvailable());
            prs.setBoolean(5, planning.getRepeatPlanning());
            prs.setInt(6, planning.getLesson().getIdLesson());
            prs.setInt(7, planning.getIdPlanning());

            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }
}
