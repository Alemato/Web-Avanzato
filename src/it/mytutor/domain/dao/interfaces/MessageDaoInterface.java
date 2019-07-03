package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Message;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface MessageDaoInterface {

     void createMessage(Message message)throws DatabaseException;
     Message getMessagebyID(int id)throws DatabaseException;
     List<Message> getAllMessagesOfChat(int chatID)throws DatabaseException;

}
