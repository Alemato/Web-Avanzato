package it.mytutor.business.impl;

import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.SubjectDao;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.util.List;

public class SubjectBusiness implements SubjectInterface {

    @Override
    public List<Subject> findAll() throws SubjectBusinessException {
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects;
        try {
            subjects = subjectDao.getAllSubject();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore prender la lista di materie");
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllStoricoStudent(String email) throws SubjectBusinessException {
        SubjectDaoInterface subjectDao = new SubjectDao();
        List<Subject> subjects;
        try {
            subjects = subjectDao.getStoricoSubjectStudent(email);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore prender la lista di materie");
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllStoricoTeacher(String email) throws SubjectBusinessException {
        SubjectDaoInterface subjectDao = new SubjectDao();
        List<Subject> subjects;
        try {
            subjects = subjectDao.getStoricoSubjectTeacher(email);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore prender la lista di materie");
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllByMacroSubject(String macroSubject) {
        return null;
    }

    @Override
    public List<Subject> findAllByMicroSubject(String microSubject) {
        return null;
    }

    @Override
    public Subject findSubjectById(Integer id) {
        return null;
    }

    @Override
    public Subject createSubject(Subject subject) {
        return null;
    }
}
