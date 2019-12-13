package it.mytutor.business.impl;

import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.LessonDao;

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
    public List<Lesson> findAllLessonByTeacher(Teacher teacher) throws DatabaseException {
        LessonDao lessonDao = new LessonDao();
        return lessonDao.getLessonsByTeacher(teacher);
    }

    @Override
    public List<Lesson> findAllLessonBySubject(Subject subject) {
        return null;
    }

    @Override
    public Lesson findLessonByID(Integer idLesson){
        return null;
    }
    @Override
    public Planning createLesson(Planning planning) {
        return planning;
    }

    @Override
    public Planning updateLessson(Planning planning) {
        return null;
    }
}

