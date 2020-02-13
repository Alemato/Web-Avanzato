package it.mytutor.business.services;

import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface LessonInterface {
    List<Lesson> findAllLessonByTeacher(Teacher teacher) throws DatabaseException, LessonBusinessException;
    List<Lesson> findAllLessonBySubject(Subject subject);
    Lesson findLessonByID(Integer idLesson) throws LessonBusinessException;
    void updateLessson(Lesson lesson) throws LessonBusinessException, SubjectBusinessException;

    Integer createLesson(Lesson lesson) throws LessonBusinessException;
}
