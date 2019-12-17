package it.mytutor.business.services;

import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface ChatInterface {
    List<Message> findAllChatByUser(String username) throws DatabaseException;
    List<Message> findAllChatByUserByQuery(String username) throws DatabaseException;
    Chat creationChat(Chat chat);
    Chat findChatByID(Integer idChat);
}
