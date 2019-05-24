package it.mytutor.domain.Dao;

import it.mytutor.domain.User;

public interface UserDaoInterface {
    //registrazione nel sistema
    public void register(User usr) throws DatabaseException;
    public void delete();
    public void login();
    public void update();

}
