package it.mytutor.business.services;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.util.List;

public interface LessonInterface {
    List<Lesson> findAllLesson ();
    List<Lesson> findAllLessonByTeacher(Teacher teacher);
    List<Lesson> findAllLessonBySubject(Subject subject);
    Lesson findLessonByFilter(String filter);
    Lesson createLesson(Lesson lesson);
    Lesson updateLessson(Lesson lesson);
}
