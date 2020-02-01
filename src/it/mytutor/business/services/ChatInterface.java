package it.mytutor.business.services;

import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface ChatInterface {
    List<Message> findAllChatByUser(String username) throws DatabaseException;

    boolean getIfExistChat(String username, Integer idUser2) throws ChatBusinessException;

    List<Chat> getChatByIdUser(int idUser) throws ChatBusinessException;

    void creationChat(User userCreate, Integer userAddressee) throws ChatBusinessException;
}
