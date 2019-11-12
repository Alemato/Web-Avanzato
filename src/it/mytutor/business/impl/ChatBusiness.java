package it.mytutor.business.impl;

import it.mytutor.business.services.ChatInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.User;

import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllChatByUser;
import static it.mytutor.business.impl.test.TestBusinness.generateChat;

public class ChatBusiness implements ChatInterface {
    // TODO COSTRUTTORE CHAT

    @Override
    public List<Chat> findAllChatByUser(User user) {
        return simulateFindAllChatByUser();
    }

    @Override
    public Chat creationChat(Chat chat) {
        return chat;
    }

    @Override
    public Chat findChatByID(Integer idChat) {
        return generateChat(idChat);
    }
}
