package it.mytutor.domain.Dao;

import it.mytutor.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao implements UserDaoInterface {
    private static final String LOGIN_STATEMENT="";
    private static final String REGISTER_STATEMENT ="insert into User(Email,Password,Name,Surname,Birthday,Language) values(?,?,?,?,?,?)";


    @Override
    public void register(User usr) throws DatabaseException{
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(REGISTER_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,usr.getEmail());
            prs.setString(2,usr.getPassword());
            prs.setString(3,usr.getName());
            prs.setString(4,usr.getSurname());
            prs.setString(5,usr.getBirtday().toString());
            prs.setString(6,usr.getLanguage().toString());
            prs.executeQuery();

        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs, prs);
        }
}
    @Override
    public void delete(){

}
    @Override
    public void login(){

}
    @Override
    public void update(){

}
}
