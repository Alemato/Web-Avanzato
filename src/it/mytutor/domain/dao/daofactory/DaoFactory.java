package it.mytutor.domain.dao.daofactory;

import it.mytutor.domain.dao.exception.DatabaseException;

import java.sql.*;

public class DaoFactory {

    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";
    private static String DRIVER = "";


    /*connessione con il database*/


    public static Connection getConnection() throws DatabaseException {
        Connection conn = null;
        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());

        }


        return conn;
    }


    public static void closeDbConnection(Connection conn, ResultSet rs, PreparedStatement prs) {
        try {
            if (rs != null)
                rs.close();
            if (prs != null)
                prs.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println();
        }

    }
}
