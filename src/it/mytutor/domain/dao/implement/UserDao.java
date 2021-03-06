package it.mytutor.domain.dao.implement;


import it.mytutor.domain.User;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.UserDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao implements UserDaoInterface {
    private static final String CREATE_USER_STATEMENT = "insert into User(Email,Roles,Password,Name,Surname,Birthday,Language,Image) values(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER_STATEMENT = "update User set Email=?,Password=?,Name=?,Surname=?,Birthday=?,Language=?,Image=? where idUser=? ";
    private static final String GET_USER_BY_ID_STATEMENT = "select * from User where idUser=?";
    private static final String GET_USER_BY_NAME_STATEMENT = "select * from User where Name=?";
    private static final String GET_USER_BY_EMAIL_STATEMENT = "select * from User where Email=?";
    private static final String GET_ALL_USER_STATEMENT = "select * from User";


    private void configureUser(User user, ResultSet resultSet) throws DatabaseException {
        try {
            user.setIdUser(resultSet.getInt("IdUser"));
            user.setEmail(resultSet.getString("Email"));
            user.setRoles(resultSet.getInt("Roles"));
            user.setPassword(resultSet.getString("Password"));
            user.setName(resultSet.getString("Name"));
            user.setSurname(resultSet.getString("Surname"));
            user.setBirthday(resultSet.getDate("Birthday"));
            user.setImage(resultSet.getString("Image"));
            user.setLanguage(resultSet.getBoolean("Language"));
            user.setCreateDate(resultSet.getTimestamp("CreateDate"));
            user.setUpdateDate(resultSet.getTimestamp("UpdateDate"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto User");
        }

    }
    private void configureUserList(List<User> users, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                User user = new User();
                configureUser(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti User");
        }
    }

    @Override
    public void createUser(User usr) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(CREATE_USER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, usr.getEmail());
            prs.setInt(2, usr.getRoles());
            prs.setString(3, usr.getPassword());
            prs.setString(4, usr.getName());
            prs.setString(5, usr.getSurname());
            prs.setDate(6, usr.getBirthday());
            prs.setBoolean(7, usr.getLanguage());
            prs.setString(8,usr.getImage());
            prs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection( conn, rs, prs);
        }
    }

    @Override
    public void modifyUser(User usr, int id) throws DatabaseException {
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(UPDATE_USER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1, usr.getEmail());
            prs.setString(2, usr.getPassword());
            prs.setString(3, usr.getName());
            prs.setString(4, usr.getSurname());
            prs.setDate(5, usr.getBirthday());
            prs.setBoolean(6, usr.getLanguage());
            prs.setString(7,usr.getImage());
            prs.setInt(8,id);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }


    @Override
    public  User getUserById(int id) throws DatabaseException {
        User user = new User();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_USER_BY_ID_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,id);
            rs = prs.executeQuery();
            if (rs.next()) {
                 configureUser(user, rs);

            } else {
                throw new DatabaseException("rs is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

        return user;
    }

    @Override
    public User getUserByName(String name) throws DatabaseException {
        User user = new User();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_USER_BY_NAME_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,name);
            rs = prs.executeQuery();
            if (rs.next()) {
                configureUser(user, rs);

            } else {
                throw new DatabaseException("rs is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws DatabaseException {
        User user = new User();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_USER_BY_EMAIL_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,email);
            rs = prs.executeQuery();
            if (rs.next()) {
                 configureUser(user, rs);

            } else {
                throw new DatabaseException("rs is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

        return user;
    }

    @Override
    public List<User> getAllUser() throws DatabaseException {
        List<User> users = new ArrayList<>();
        Connection conn = DaoFactory.getConnection();
        if (conn == null) {
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs = null;
        PreparedStatement prs = null;
        try {
            prs = conn.prepareStatement(GET_ALL_USER_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            rs = prs.executeQuery();
            configureUserList(users, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());

        } finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

        return users;
    }






}
