package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Chat;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface ChatDaoInterface {
    List<Chat> getAllChatByIdUser(int idUser) throws DatabaseException;
}
