package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Creates;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface CreatesDaoInterface {
 void createCreates(Creates creates) throws DatabaseException;
 void updateCreates(Creates creates) throws DatabaseException;
 Creates getCreates(int id) throws DatabaseException;
 List<Creates> getAllCreates() throws DatabaseException;
}
