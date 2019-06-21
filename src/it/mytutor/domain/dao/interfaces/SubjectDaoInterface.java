package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;

public interface SubjectDaoInterface {

    Subject getSubjectById(int id)throws DatabaseException;

    Subject getSubjectByName(String subjectName) throws DatabaseException;

    void createSubject(Subject subject)throws DatabaseException;

    void modifySubjectByID(Subject subject,int id)throws DatabaseException;



    List<Subject> getAllSubject () throws DatabaseException;

}
