package it.mytutor.business.impl;

import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.util.List;

public class LessonBusiness implements LessonInterface {
    //TODO Costruttore Lesson

    @Override
    public List<Lesson> findAllLesson() {
        return null;
    }

    @Override
    public List<Lesson> findAllLessonByTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public List<Lesson> findAllLessonBySubject(Subject subject) {
        return null;
    }

    @Override
    public Lesson findLessonByFilter(String filter) {
        return null;
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        return null;
    }

    @Override
    public Lesson updateLessson(Lesson lesson) {
        return null;
    }
}
