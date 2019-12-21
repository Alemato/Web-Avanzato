package it.mytutor.domain.dao.implement;

import it.mytutor.domain.*;

import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;
import it.mytutor.domain.dao.interfaces.ChatDaoInterface;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ChatDao implements ChatDaoInterface {
    private static final String CREATE_A_CHAT = "insert into Chat(Name,CreateDate, UpdateDate) value (?, DEFAULT , DEFAULT)";
    private static final String GET_ALL_CHAT_BY_ID_USER = "select distinct t.idchat, t.name, t.createdate, t.updatedate from Chat t, Message s Where s.IdChat=t.IdChat and s.IdUser = ? ORDER BY CreateDate DESC LIMIT 10";

    private void configureChat(Chat chat, ResultSet resultSet) throws DatabaseException {
        try{
            chat.setIdChat(resultSet.getInt("IdChat"));
            chat.setName(resultSet.getString("Name"));
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
                configureChat(chat, resultSet);
                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Chat");
        }
    }

    @Override
    public void crateAChat(Chat chat) throws DatabaseException {
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
            prs.setString(1, chat.getName());
            prs.executeUpdate();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(connection, rs, prs);
        }
    }

    @Override
    public List<Chat> getAllChatByIdUser(int idUser) throws DatabaseException {
        List<Chat> chatList = new ArrayList<Chat>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_CHAT_BY_ID_USER);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1, idUser);
            rs = prs.executeQuery();
            configureChatList(chatList, rs);
            if (chatList.isEmpty()) {
                 throw new DatabaseException("rs is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
        return chatList;

    }
}
