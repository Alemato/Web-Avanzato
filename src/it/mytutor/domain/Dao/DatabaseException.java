package it.mytutor.domain.Dao;


public class DatabaseException extends Exception  {


    public DatabaseException()  {
        super();
    }

    public DatabaseException(String msg)  {
        super(msg);
    }
}