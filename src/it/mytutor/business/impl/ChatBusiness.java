package it.mytutor.business.impl;

import it.mytutor.business.services.ChatInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.ChatDao;
import it.mytutor.domain.dao.implement.MessageDao;
import it.mytutor.domain.dao.implement.UserDao;

import java.util.ArrayList;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllChatByUser;
import static it.mytutor.business.impl.test.TestBusinness.generateChat;

public class ChatBusiness implements ChatInterface {
    // TODO COSTRUTTORE CHAT

    private User getUser(String username) throws DatabaseException{
        UserDao userDao = new UserDao();
        return userDao.getUserByEmail(username);
    }

    @Override
    public List<Message> findAllChatByUserByQuery(String username) throws DatabaseException {
        User user = getUser(username);
        List<Message> messageList = new ArrayList<Message>();
        ChatDao chatDao = new ChatDao();
        List<Chat> chatList = chatDao.getAllChatByIdUser(user.getIdUser());
        for (Chat chat: chatList) {
            MessageDao messageDao = new MessageDao();
            List<Message> messages = messageDao.getAllMessagesOfChat(chat.getIdChat());
            messageList.addAll(messages);
        }
        return messageList;
    }

    @Override
    public List<Message> findAllChatByUser(String username) throws DatabaseException {
        User user = getUser(username);
        List<Message> messageList = new ArrayList<Message>();
        ChatDao chatDao = new ChatDao();
        List<Chat> chatList = chatDao.getAllChatByIdUser(user.getIdUser());
        for (Chat chat: chatList) {
            MessageDao messageDao = new MessageDao();
            messageList.add(messageDao.getAMessageOfChat(chat.getIdChat()));
        }
        return messageList;
    }

    @Override
    public Chat creationChat(Chat chat) {
        return chat;
    }

    @Override
    public Chat findChatByID(Integer idChat) {
        return generateChat(idChat);
    }
}
