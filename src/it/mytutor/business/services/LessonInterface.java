package it.mytutor.business.services;

import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface LessonInterface {
//    List<Lesson> findAllLesson () throws LessonBusinessException;
    List<Lesson> findAllLessonByTeacher(Teacher teacher) throws DatabaseException, LessonBusinessException;
    List<Lesson> findAllLessonBySubject(Subject subject);
//    TODO da fare la logica del metodo findLessonByFilter sulla business
//    List<Lesson> findLessonByFilter(String macroMateria, String nome, String zona, String microMateria,
//                                    String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException;
    Lesson findLessonByID(Integer idLesson);
    Planning createLesson(Planning planning);
    void updateLessson(Lesson lesson) throws LessonBusinessException, SubjectBusinessException;
}
