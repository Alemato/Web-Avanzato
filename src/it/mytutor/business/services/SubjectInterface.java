package it.mytutor.business.services;

import it.mytutor.domain.Subject;

import java.util.List;

public interface SubjectInterface {
    List<Subject> findAll();
    List<Subject> findAllByMacroSubject(String macroSubject);
    List<Subject> findAllByMicroSubject(String microSubject);
    Subject findSubjectById(Integer id);
    Subject createSubject(Subject subject);
}
