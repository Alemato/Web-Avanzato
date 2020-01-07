package it.mytutor.business.services;

import it.mytutor.business.exceptions.MessageBusinessException;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.util.List;

public interface MessageInterface {
    List<Message> findAllMessageByChat(int idChat) throws MessageBusinessException;
    Message getMessageById(int id);
//    Boolean getIfNewMessages(int id);
    List<Message> getNewMessagesByIdLast(Integer idChat, Integer idLastMessage) throws MessageBusinessException;
    List<Message> findAllMessageByUser(User user);
    void crateMessage(Message message) throws MessageBusinessException;
}
