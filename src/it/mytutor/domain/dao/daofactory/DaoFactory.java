package it.mytutor.domain.dao.daofactory;

import com.mysql.jdbc.ConnectionImpl;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.sql.*;

public class DaoFactory {

    private static String URL = "jdbc:mysql://localhost/mytutor";
    private static String USER = " ";
    private static String PASSWORD = " ";
    private static String DRIVER = "com.mysql.jdbc.Driver";


    /*connessione con il database*/


    public static Connection getConnection() throws DatabaseException {
        Connection conn = null;
        try {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    public static void closeDbConnection(Connection conn, ResultSet rs, PreparedStatement prs) {
        try {
            if (rs != null)
                rs.close();
                rs = null;
            if (prs != null)
                prs.close();
                prs = null;
            if (conn != null)
                conn.commit();
                conn.close();
                conn = null;
        } catch (SQLException e) {
            System.out.println();
        }

    }
}
