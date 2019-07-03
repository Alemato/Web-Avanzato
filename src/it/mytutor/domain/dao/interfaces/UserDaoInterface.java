package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;

public interface UserDaoInterface {
    void createUser(User usr) throws DatabaseException;



    void modifyUser(User usr, int id) throws DatabaseException;

    User getUserById(int id) throws DatabaseException;

    User getUserByName(String name) throws DatabaseException;

    User getUserByEmail(String email) throws DatabaseException;

    List<User> getAllUser() throws DatabaseException;

   /* Boolean authentication(User usr, String pwd) throws DatabaseException;
    //da fare */

}
