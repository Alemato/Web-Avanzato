package it.mytutor.business.impl;

import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllMessageByChat;

public class MessageBusiness implements MessageInterface {
    // TODO costruttore Messaggio

    @Override
    public List<Message> findAllMessageByChat(Chat chat) {

        return  simulateFindAllMessageByChat();
    }

    @Override
    public List<Message> findAllMessageByUser(User user) {
        return simulateFindAllMessageByChat();
    }

    @Override
    public Message crateMessage(Message message) {
        return null;
    }
}
