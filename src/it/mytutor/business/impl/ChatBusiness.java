package it.mytutor.business.impl;

import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.business.services.ChatInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.ChatDao;
import it.mytutor.domain.dao.implement.MessageDao;
import it.mytutor.domain.dao.implement.UserDao;
import it.mytutor.domain.dao.interfaces.UserDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class ChatBusiness implements ChatInterface {

    private User getUser(String username) throws DatabaseException{
        UserDao userDao = new UserDao();
        return userDao.getUserByEmail(username);
    }

    @Override
    public List<Message> findAllChatByUser(String username) throws DatabaseException {
        User user = getUser(username);
        List<Message> messageList = new ArrayList<>();
        ChatDao chatDao = new ChatDao();
        List<Chat> chatList = chatDao.getChatByIdUser(user.getIdUser());
        if (!chatList.isEmpty()) {
            for (Chat chat : chatList) {
                MessageDao messageDao = new MessageDao();
                messageList.add(messageDao.getAMessageOfChat(chat.getIdChat()));
            }
        }
        return messageList;
    }

    @Override
    public boolean getIfExistChat(String username, Integer idUser2) throws ChatBusinessException {
        List<Chat> chats;
        ChatDao chatDao = new ChatDao();
        UserDao userDao = new UserDao();
        User user;
        boolean ifExist = false;
        try {
            user = userDao.getUserByEmail(username);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere l'oggetto user");
        }
        try {

            chats = chatDao.getChatByIdUser(user.getIdUser());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere gli oggetti creates");
        }
        // io student
        if (user.getRoles() == 1) {
            for (Chat chat : chats) {
                if (((User) chat.getUserListser().get(1)).getIdUser().equals(idUser2)){
                    ifExist = true;
                    break;
                }
            }
        }
        // io teacher
        else {
            for (Chat chat : chats) {
                if (((User) chat.getUserListser().get(0)).getIdUser().equals(idUser2)){
                    ifExist = true;
                    break;
                }
            }
        }

        return ifExist;
    }

    @Override
    public List<Chat> getChatByIdUser(int idUser) throws ChatBusinessException {
        List<Chat> chats;
        ChatDao chatDao = new ChatDao();
        try {

            chats = chatDao.getChatByIdUser(idUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere gli oggetti creates");
        }

        return chats;
    }

    @Override
    public void creationChat(User userCreate, Integer addressee) throws ChatBusinessException {
        ChatDao chatDao = new ChatDao();
        UserDao userDao = new UserDao();
        User userAddressee;

        try {
            userAddressee = userDao.getUserById(addressee);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere l'user destintario");
        }

        if (userCreate.getRoles() == 1) {
            try {
                chatDao.crateAChat(userCreate.getIdUser(), userAddressee.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nella creazione della chat");
            }
        } else {
            try {
                chatDao.crateAChat(userAddressee.getIdUser(), userCreate.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nella creazione della chat");
            }
        }
    }
}
