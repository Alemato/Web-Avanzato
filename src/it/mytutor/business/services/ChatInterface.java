package it.mytutor.business.services;

import it.mytutor.domain.Chat;
import it.mytutor.domain.User;

import java.util.List;

public interface ChatInterface {
    List<Chat> findAllChatByUser(User user);
    Chat creationChat(Chat chat);
    Chat findChatByID(Integer idChat);
}
