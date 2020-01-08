package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Creates;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface CreatesDaoInterface {
 void createCreates(Integer idUser1, Integer idChat, Integer idUser2) throws DatabaseException;

    List<Creates> getCreatesByIdUser(Integer idUser) throws DatabaseException;

    void updateCreates(Creates creates) throws DatabaseException;
 Creates getCreates(int id) throws DatabaseException;
 List<Creates> getAllCreates() throws DatabaseException;
}
