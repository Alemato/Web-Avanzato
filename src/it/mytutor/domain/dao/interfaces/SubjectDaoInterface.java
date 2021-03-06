package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;

public interface SubjectDaoInterface {

    Subject getSubjectById(int id)throws DatabaseException;

    List<Subject> getSubjectsByName(String subjectName) throws DatabaseException;

    Integer createSubject(Subject subject)throws DatabaseException;

    void modifySubjectByID(Subject subject)throws DatabaseException;



    List<Subject> getAllSubject () throws DatabaseException;

    List<Subject> getStoricoSubjectStudent(String email) throws DatabaseException;

    List<Subject> getStoricoSubjectTeacher(String email) throws DatabaseException;
}
