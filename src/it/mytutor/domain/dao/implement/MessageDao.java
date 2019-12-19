package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Message;
import it.mytutor.domain.Chat;
import it.mytutor.domain.User;
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
    private static final String GET_ALL_MESSAGE_OF_CHAT_STATEMENT = "select * from Message m , Chat c, User u where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC";
    private static final String GET_ALL_MESSAGE_OF_CHAT_STATEMENT_LIMIT_5 = "select * from Message m , Chat c, User u where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC LIMIT 5";
    private static final String GET_A_MESSAGE_OF_CHAT_STATEMEN = "select * from Message m, Chat c, User u where m.IdChat = c.IdChat AND m.IdUser = u.IdUser AND m.IdChat = ? ORDER BY m.SendDate DESC LIMIT 1";

    private void configureMessage(Message message, Chat chat, User user, ResultSet resultSet) throws DatabaseException {
        try {
            message.setIdMessage(resultSet.getInt("idMessage"));
            message.setText(resultSet.getString("Text"));
            message.setSendDate(resultSet.getTimestamp("SendDate"));
            message.setCreateDate(resultSet.getTimestamp("m.CreateDate"));
            message.setUpdateDate(resultSet.getTimestamp("m.UpdateDate"));
            chat.setIdChat(resultSet.getInt("c.IdChat"));
            chat.setName(resultSet.getString("c.Name"));
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
                configureMessage(message, chat, user, resultSet);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Messaggi");
        }
    }


    @Override
    public void createMessage(Message message) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(CREATE_MESSAGE_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,message.getText());
            prs.setTimestamp(2,message.getSendDate());
            prs.setObject(3,message.getChat());
            prs.setObject(4,message.getUser());
            prs.executeUpdate();

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }

    }

    @Override
    public Message getMessagebyID(int id) throws DatabaseException {
        Message message = new Message();
        Chat chat = new Chat();
        User user = new User();
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
            rs=prs.executeQuery();
            if(rs.next()){
                configureMessage(message, chat, user, rs);
            }else{
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
        List<Message> messages=new ArrayList<Message>();
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
            rs=prs.executeQuery();
            configureMessageList(messages,rs);
            if(messages.isEmpty()){
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
        List<Message> messages=new ArrayList<Message>();
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
            rs=prs.executeQuery();
            configureMessageList(messages,rs);
            if(messages.isEmpty()){
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
            rs=prs.executeQuery();
            if(rs.next()){
                configureMessage(message, chat, user, rs);
            }else{
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
