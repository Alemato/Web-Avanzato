package it.mytutor.business.impl;

import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.domain.Subject;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.SubjectDao;

import java.util.ArrayList;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAll;

public class SubjectBusiness implements SubjectInterface {
    //TODO costruttore Subject

    @Override
    public List<Subject> findAll() throws SubjectBusinessException {
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = new ArrayList<Subject>();
        try {
            subjects = subjectDao.getAllSubject();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore prender la lista di materie");
        }
        return subjects;
    }

    @Override
    public List<Subject> findAllStorico (String email) throws SubjectBusinessException {
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = new ArrayList<Subject>();
        try {
            subjects = subjectDao.getStoricoSubject(email);
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
