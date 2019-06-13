package it.mytutor.domain.dao.exception;


public class DatabaseException extends Exception  {


    public DatabaseException()  {
        super();
    }

    public DatabaseException(String msg)  {
        super(msg);
    }
}
