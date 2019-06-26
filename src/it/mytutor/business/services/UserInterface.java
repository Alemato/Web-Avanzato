package it.mytutor.business.services;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.domain.User;

public interface UserInterface {
    Object findUserByUsername(String username) throws UserException;
    Object autentication(String username, String password) throws UserException;
}
