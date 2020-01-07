package it.mytutor.business.services;

import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.domain.Subject;

import java.util.List;

public interface SubjectInterface {
    List<Subject> findAll() throws SubjectBusinessException;


    List<Subject> findAllStoricoStudent(String email) throws SubjectBusinessException;

    List<Subject> findAllStoricoTeacher(String email) throws SubjectBusinessException;

    List<Subject> findAllByMacroSubject(String macroSubject);
    List<Subject> findAllByMicroSubject(String microSubject);
    Subject findSubjectById(Integer id);
    Subject createSubject(Subject subject);
}
