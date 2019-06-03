package it.mytutor.business.impl;

import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.util.List;

public class MessageBusiness implements MessageInterface {
    // TODO costruttore Messaggio

    @Override
    public List<Message> findAllMessageByChat(Chat chat) {
        return null;
    }

    @Override
    public List<Message> findAllMessageByUser(User user) {
        return null;
    }

    @Override
    public Message crateMessage(Message message) {
        return null;
    }
}
