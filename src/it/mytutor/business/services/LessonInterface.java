package it.mytutor.business.services;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.util.List;

public interface LessonInterface {
    List<Lesson> findAllLesson ();
    List<Lesson> findAllLessonByTeacher(Teacher teacher);
    List<Lesson> findAllLessonBySubject(Subject subject);
//    TODO da fare la logica del metodo findLessonByFilter sulla business
    List<Lesson> findLessonByFilter(String macroMateria, String nome, String zona, String microMateria,
                                    String giornoSettimana, String prezzo, String oraInizio, String oraFine);
    Lesson findLessonByID(Integer idLesson);
    Lesson createLesson(Lesson lesson);
    Lesson updateLessson(Lesson lesson);
}
