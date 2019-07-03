package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Planning;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanningDao implements PlanningDaoInterface {
    private static final String CREATE_PLANNING_STATEMENT="insert into planning(Date,StartTime,EndTime,IdLesson) values(?,?,?,?,?)";
    private static final String UPDATE_PLANNING_STATEMENT="update into planning set (Date=?,StartTime=?,EndTime=?,IdLesson=?) where id=?";
    private static final String GET_PLANNING_BY_STUDENT_STATEMENT="";
    private static final String GET_PLANNING_BY_ID_STATEMENT="";
    private static final String GET_ALL_PLANNING_OF_A_STATEMENT="";

    @Override
    public void createPlanning(Planning planning) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(CREATE_PLANNING_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setObject(1,planning.getDate());
            prs.setTime(2,planning.getStartTime());
            prs.setTime(3,planning.getEndTime());
            prs.setInt(4,planning.getLesson().getIdLesson());

            prs.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void updatePlanning(Planning planning) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(UPDATE_PLANNING_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setObject(1,planning.getDate());
            prs.setTime(2,planning.getStartTime());
            prs.setTime(3,planning.getEndTime());
            prs.setInt(4,planning.getLesson().getIdLesson());
            prs.setInt(5,planning.getIdPlanning());

            prs.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }

    }
}
