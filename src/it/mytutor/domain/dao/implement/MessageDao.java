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
    private static final String CREATE_MESSAGE_STATEMENT="insert into Message(text,sendDate,IdChat,IdUser) values (?,?,?,?)";
    private static final String UPDATE_MESSAGE_STATEMENT="update Message set text=?, sendDate=?, IdChat=?, IdUser=? where IdMessage=?";
    private static final String GET_MESSAGE_BY_ID_STATEMENT="select * from Message where IdMessage=?";
    private static final String GET_ALL_MESSAGE_OF_CHAT_STATEMENT="select * from Subject where IdChat=?";

    private void configureMessage(Message message, ResultSet resultSet) throws DatabaseException {
        try {
            message.setIdMessage(resultSet.getInt("idMessage"));
            message.setText(resultSet.getString("text"));
            message.setSendDate(resultSet.getTimestamp("sendDate"));
            message.setCreateDate(resultSet.getTimestamp("createDate"));
            message.setUpdateDate(resultSet.getTimestamp("updateDate"));
            message.setIdChat((Chat)resultSet.getObject("chat"));
            message.setIdUser((List<User>)resultSet.getObject("user"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Subject");
        }
    }

    private void configureMessageList(List<Message> messages, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                Message message = new Message();
                configureMessage(message, resultSet);
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
            prs.setObject(3,message.getIdChat());
            prs.setObject(4,message.getIdUser());
            prs.executeQuery();

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }

    }


    @Override
    public void modifyMessage(Message message) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(UPDATE_MESSAGE_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,message.getText());
            prs.setTimestamp(2,message.getSendDate());
            prs.setObject(3,message.getIdChat());
            prs.setObject(4,message.getIdUser());
            prs.setInt(5,message.getIdMessage());

            prs.executeUpdate();

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }

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
            if(rs.next()){
                configureMessageList(messages,rs);
            }else{
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
    public Message getMessagebyID(int id) throws DatabaseException {
        Message message=new Message();
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
            configureMessage(message,rs);
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
