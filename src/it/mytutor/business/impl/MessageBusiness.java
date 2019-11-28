package it.mytutor.business.impl;

import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.Message;
import it.mytutor.domain.User;

import java.util.ArrayList;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllMessageByChat;
import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllMessageByUser;

public class MessageBusiness implements MessageInterface {
    // TODO costruttore Messaggio

    @Override
    public List<Message> findAllMessageByChat(Chat chat) {
        List<Message> messages = new ArrayList<>();
        try {
            messages.addAll(simulateFindAllMessageByChat(chat));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  messages;
    }

    @Override
    public Message getMessageById(int id){ return null; }

//    @Override
//    public boolean getIfNewMessages(int id){ return true; }

    @Override
    public List<Message> getNewMessagesByIdLast(int id){ return null; }

    @Override
    public List<Message> findAllMessageByUser(User user) {
        return simulateFindAllMessageByUser(user);
    }

    @Override
    public Message crateMessage(Message message) {
        return null;
    }
}
