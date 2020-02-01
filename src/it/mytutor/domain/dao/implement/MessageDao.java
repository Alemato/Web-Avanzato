package it.mytutor.domain.dao.implement;

import it.mytutor.domain.*;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.MessageDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao implements MessageDaoInterface {
    private static final String CREATE_MESSAGE_STATEMENT = "insert into Message(text,sendDate,IdChat,IdUser) values (?,?,?,?)";
    private static final String GET_MESSAGE_BY_ID_STATEMENT = "select * from Message m, Chat c, User u where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND IdMessage = ?";
    private static final String GET_ALL_MESSAGE_OF_CHAT_STATEMENT = "select * from Message m, Chat c, User u, User u1, Student s, User u2, Teacher t where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND u1.IdUser = c.IdUser1 and s.IdUser = u1.IdUser and u2.IdUser = c.IDUser2 and t.IdUser = u2.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC, m.IdMessage DESC";
    private static final String GET_ALL_MESSAGE_OF_CHAT_STATEMENT_LIMIT_5 = "select * from Message m , Chat c, User u where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC, m.IdMessage DESC LIMIT 5";
    private static final String GET_A_MESSAGE_OF_CHAT_STATEMEN = "select * from Message m, Chat c, User u, User u1, Student s, User u2, Teacher t where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND u1.IdUser = c.IdUser1 and s.IdUser = u1.IdUser and u2.IdUser = c.IDUser2 and t.IdUser = u2.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC, m.IdMessage DESC LIMIT 1";

    private void configureMessage(Message message, Chat chat, User user, List<Object> users, ResultSet resultSet) throws DatabaseException {
        try {
            message.setIdMessage(resultSet.getInt("IdMessage"));
            message.setText(resultSet.getString("Text"));
            message.setSendDate(resultSet.getTimestamp("SendDate"));
            message.setCreateDate(resultSet.getTimestamp("m.CreateDate"));
            message.setUpdateDate(resultSet.getTimestamp("m.UpdateDate"));
            chat.setIdChat(resultSet.getInt("c.IdChat"));

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
            chat.setCreateDate(resultSet.getTimestamp("c.CreateDate"));
            chat.setUpdateDate(resultSet.getTimestamp("c.UpdateDate"));

            message.setChat(chat);
            user.setIdUser(resultSet.getInt("u.IdUser"));
            user.setEmail(resultSet.getString("Email"));
            user.setRoles(resultSet.getInt("Roles"));
            // salto password cosi che sia null
            user.setName(resultSet.getString("u.Name"));
            user.setSurname(resultSet.getString("Surname"));
            user.setBirthday(resultSet.getDate("Birthday"));
            user.setLanguage(resultSet.getBoolean("Language"));
            user.setImage(resultSet.getString("Image"));
            user.setCreateDate(resultSet.getTimestamp("u.CreateDate"));
            user.setUpdateDate(resultSet.getTimestamp("u.UpdateDate"));
            message.setUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Message");
        }
    }

    private void configureMessageList(List<Message> messages, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Message message = new Message();
                Chat chat = new Chat();
                User user = new User();
                List<Object> users = new ArrayList<>();
                configureMessage(message, chat, user, users, resultSet);
                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Messaggi");
        }
    }


    @Override
    public void createMessage(Message message) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_MESSAGE_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, message.getText());
            prs.setTimestamp(2, message.getSendDate());
            prs.setInt(3, message.getChat().getIdChat());
            prs.setInt(4, message.getUser().getIdUser());
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }

    @Override
    public Message getMessagebyID(int id) throws DatabaseException {
        Message message = new Message();
        Chat chat = new Chat();
        User user = new User();
        List<Object> users = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_MESSAGE_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, id);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureMessage(message, chat, user, users, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return message;
    }


    @Override
    public List<Message> getAllMessagesOfChat(int chatID) throws DatabaseException {
        List<Message> messages = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_MESSAGE_OF_CHAT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, chatID);
            rs = prs.executeQuery();
            configureMessageList(messages, rs);
            if (messages.isEmpty()) {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return messages;
    }


    @Override
    public List<Message> getAllMessagesOfChatLimitFive(int chatID) throws DatabaseException {
        List<Message> messages = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_MESSAGE_OF_CHAT_STATEMENT_LIMIT_5);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, chatID);
            rs = prs.executeQuery();
            configureMessageList(messages, rs);
            if (messages.isEmpty()) {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return messages;
    }


    @Override
    public Message getAMessageOfChat(int chatID) throws DatabaseException {
        Chat chat = new Chat();
        User user = new User();
        Message message = new Message();
        List<Object> users = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_A_MESSAGE_OF_CHAT_STATEMEN);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, chatID);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureMessage(message, chat, user, users, rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);

        }
        return message;
    }
}
