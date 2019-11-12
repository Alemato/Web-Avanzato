package it.mytutor.business.impl;

import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllLesson;

public class LessonBusiness implements LessonInterface {
    //TODO Costruttore Lesson

    @Override
    public List<Lesson> findAllLesson() {

        List<Lesson> lessons = null;
        try {
            lessons = new ArrayList<>(simulateFindAllLesson());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessons;

    }

    @Override
    public List<Lesson> findAllLessonByTeacher(Teacher teacher) {

        List<Lesson> lessons = null;
        try {
            lessons = new ArrayList<>(simulateFindAllLesson());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    @Override
    public List<Lesson> findAllLessonBySubject(Subject subject) {
        return null;
    }

    @Override
    public List<Lesson> findLessonByFilter(String filter) {
        return null;
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        return lesson;
    }

    @Override
    public Lesson updateLessson(Lesson lesson) {
        return null;
    }
}

