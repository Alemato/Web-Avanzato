package it.mytutor.business.services;

import it.mytutor.business.exceptions.MessageBusinessException;
import it.mytutor.domain.Message;

import java.util.List;

public interface MessageInterface {
    List<Message> findAllMessageByChat(int idChat) throws MessageBusinessException;
    List<Message> getNewMessagesByIdLast(Integer idChat, Integer idLastMessage) throws MessageBusinessException;
    void crateMessage(Message message) throws MessageBusinessException;
}
