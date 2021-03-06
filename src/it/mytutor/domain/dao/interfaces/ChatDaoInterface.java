package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Chat;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface ChatDaoInterface {
    Integer crateAChat(Integer idUserS, Integer idUserT) throws DatabaseException;


    List<Chat> getChatByIdUser(Integer idUser) throws DatabaseException;

    Chat getChatById(Integer idChat) throws DatabaseException;
}
