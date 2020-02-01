package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Chat;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.ChatDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChatDao implements ChatDaoInterface {
    private static final String CREATE_A_CHAT = "insert into Chat(IdUser1, IdUser2) value (?,?)";

private static final String GET_CHATS_BY_ID_USER_STATEMENT = "select * from Chat c, User u1, Student s, User u2, Teacher t where (c.IdUser1 = ? or c.IdUser2 = ?) and u1.IdUser = c.IdUser1 and s.IdUser = u1.IdUser and u2.IdUser = c.IDUser2 and t.IdUser = u2.IdUser";

    private void configureChat(Chat chat, List<Object> users, ResultSet resultSet) throws DatabaseException {
        try{
            Student student = new Student();
            student.setIdStudent(resultSet.getInt("s.IdStudent"));
            student.setStudyGrade(resultSet.getString("s.StudyGrade"));
            student.setCreateDateStudent(resultSet.getTimestamp("s.CreateDate"));
            student.setUpdateDateStudent(resultSet.getTimestamp("s.UpdateDate"));
            student.setIdUser(resultSet.getInt("s.IdUser"));
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

            users.add(student);
            Teacher teacher = new Teacher();

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
            users.add(teacher);
            chat.setUserListser(users);

            chat.setIdChat(resultSet.getInt("IdChat"));

            chat.setCreateDate(resultSet.getTimestamp("CreateDate"));
            chat.setUpdateDate(resultSet.getTimestamp("UpdateDate"));
        } catch (SQLException s) {
            s.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Chat");
        }

    }

    private void configureChatList(List<Chat> chats, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Chat chat = new Chat();
                List<Object> users = new ArrayList<>();
                configureChat(chat, users, resultSet);
                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Chat");
        }
    }

    @Override
    public void crateAChat(Integer idUserS, Integer idUserT) throws DatabaseException {
        Connection connection = DaoFactory.getConnection();
        if(connection == null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = connection.prepareStatement(CREATE_A_CHAT);
            if (prs == null){
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idUserS);
            prs.setInt(2, idUserT);
            prs.executeUpdate();

        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(connection, rs, prs);
        }
    }

    @Override
    public List<Chat> getChatByIdUser(Integer idUser) throws DatabaseException {
        List<Chat> chatList = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_CHATS_BY_ID_USER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idUser);
            prs.setInt(2, idUser);
            rs = prs.executeQuery();
            configureChatList(chatList, rs);


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return chatList;
    }
}
