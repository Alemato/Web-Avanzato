package it.mytutor.business.services;

import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.util.List;

public interface MessageInterface {
    List<Message> findAllMessageByChat(Chat chat);
    Message getMessageById(int id);
//    Boolean getIfNewMessages(int id);
    List<Message> getNewMessagesByIdLast(int id);
    List<Message> findAllMessageByUser(User user);
    Message crateMessage(Message message);
}
