package it.mytutor.domain.dao.implement;

import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.daofactory.DaoFactory;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao implements SubjectDaoInterface {
    private static final String GET_SUBJECT_BY_ID_STATEMENT="select * from Subject where IdSubject=?";
    private static final String GET_SUBJECT_BY_NAME_STATEMENT="select * from Subject where macroSubject=?";
    private static final String UPDATE_SUBJECT_STATEMENT="update Subject set macroSubject=?, microSubject=? where idSubject=?";
    private static final String CREATE_SUBJECT_STATEMENT="insert into Subject(macroSubject,microSubject) values(?,?)";
    private static final String GET_ALL_SUBJECT_STATEMENT="select * from Subject";


    private void configureSubject(Subject subject, ResultSet resultSet) throws DatabaseException {
        try {
            subject.setIdSubject(resultSet.getInt("IdSubject"));
            subject.setMacroSubject(resultSet.getString("macroSubject"));
            subject.setMicroSubject(resultSet.getString("microSubject"));
            subject.setCreateDate(resultSet.getTimestamp("createDate"));
            subject.setCreateDate(resultSet.getTimestamp("updateDate"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare oggetto Subject");
        }
    }
    private void configureSubjectList(List<Subject> users, ResultSet resultSet) throws DatabaseException {
        try {
            while (resultSet.next()) {
                // Subject subject = new Subject();
                // configureSubject(subject, resultSet);
                // users.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Errore nel creare Lista oggetti Subject");
        }
    }

    @Override
    public Subject getSubjectById(int id) throws DatabaseException {
        Subject subject=null;
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
           prs=conn.prepareStatement(GET_SUBJECT_BY_ID_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setInt(1,id );
            rs = prs.executeQuery();
            if(rs.next()) {
                configureSubject(subject,rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }
        return subject;
    }

    @Override
    public Subject getSubjectByName(String subjectName) throws DatabaseException{
        Subject subject=null;
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(GET_SUBJECT_BY_NAME_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,subjectName );
            rs = prs.executeQuery();
            if(rs.next()) {
                configureSubject(subject,rs);
            } else {
                throw new DatabaseException("rs is empty");
            }
        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }
        return subject;
    }

    @Override
    public void createSubject(Subject subject) throws DatabaseException {
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs=conn.prepareStatement(CREATE_SUBJECT_STATEMENT);
            if(prs==null){
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,subject.getMacroSubject());
            prs.setString(2,subject.getMicroSubject());
            prs.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn, rs, prs);
        }
    }

    @Override
    public void modifySubjectByID(Subject subject, int id) throws DatabaseException{
        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs = conn.prepareStatement(UPDATE_SUBJECT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            prs.setString(1,subject.getMacroSubject());
            prs.setString(2,subject.getMicroSubject());
            prs.setInt(3,id);
            prs.executeUpdate();
        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }
    }

    @Override
    public List<Subject> getAllSubject() throws DatabaseException {
        List<Subject> Subejcts= new ArrayList<Subject>();

        Connection conn= DaoFactory.getConnection();
        if (conn==null){
            throw new DatabaseException("Connection is null");
        }
        ResultSet rs=null;
        PreparedStatement prs=null;
        try {
            prs = conn.prepareStatement(GET_ALL_SUBJECT_STATEMENT);
            if (prs == null) {
                throw new DatabaseException("Statement is null");
            }
            rs=prs.executeQuery();
            configureSubjectList(Subejcts,rs);

        }catch(SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            DaoFactory.closeDbConnection(conn,rs,prs);
        }
        return Subejcts;
    }

  }
