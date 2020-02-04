package it.mytutor.business.services;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

public interface UserInterface {
    Object findUserByUsername(String username) throws UserException, DatabaseException;

    Object findUserById(String idUser) throws UserException, DatabaseException;

    void editUser(Object user) throws UserException, DatabaseException;
    Object autentication(String username, String password) throws UserException, DatabaseException;
}
