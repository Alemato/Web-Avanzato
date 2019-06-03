package it.mytutor.business.impl;

import it.mytutor.business.services.SubjectInterface;
import it.mytutor.domain.Subject;

import java.util.List;

public class SubjectBusiness implements SubjectInterface {
    //TODO costruttore Subject

    @Override
    public List<Subject> findAll() {
        return null;
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
