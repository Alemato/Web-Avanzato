package it.mytutor.business.impl;

import it.mytutor.business.exceptions.MessageBusinessException;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Message;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.MessageDao;
import it.mytutor.domain.dao.interfaces.MessageDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class MessageBusiness implements MessageInterface {

    @Override
    public List<Message> findAllMessageByChat(int idChat) throws MessageBusinessException {
        List<Message> messages;
        MessageDaoInterface messageDao = new MessageDao();
        try {
            messages = messageDao.getAllMessagesOfChat(idChat);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new MessageBusinessException("Errore nel prendere i messaggi");
        }
        return  messages;
    }

    @Override
    public List<Message> getNewMessagesByIdLast(Integer idChat, Integer idLastMessage) throws MessageBusinessException {
        List<Message> messages;
        List<Message> messages1 = new ArrayList<>();
        MessageDaoInterface messageDao = new MessageDao();
        try {
            messages = messageDao.getAllMessagesOfChat(idChat);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new MessageBusinessException("Errore nel prendere i messaggi");
        }
        for (Message message : messages) {
            if (message.getIdMessage().equals(idLastMessage)) {
                break;
            }
            messages1.add(message);
        }
        return messages1;
    }

    @Override
    public void crateMessage(Message message) throws MessageBusinessException {
        MessageDao messageDao = new MessageDao();
        try {
            messageDao.createMessage(message);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new MessageBusinessException("Errore nella creazione del messaggio");
        }
    }
}
