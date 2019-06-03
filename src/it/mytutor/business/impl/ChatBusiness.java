package it.mytutor.business.impl;

import it.mytutor.business.services.ChatInterface;
import it.mytutor.domain.Chat;
import it.mytutor.domain.User;

import java.util.List;

public class ChatBusiness implements ChatInterface {
    // TODO COSTRUTTORE CHAT

    @Override
    public List<Chat> findAllChatByUser(User user) {
        return null;
    }

    @Override
    public Chat creationChat(Chat chat) {
        return null;
    }

    @Override
    public Chat findChatByID(Integer idChat) {
        return null;
    }
}
