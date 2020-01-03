package it.mytutor.business.impl;

import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.LessonDao;
import it.mytutor.domain.dao.implement.SubjectDao;
import it.mytutor.domain.dao.interfaces.LessonDaoInterface;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.util.List;

public class LessonBusiness implements LessonInterface {

    /*@Override
    public List<Lesson> findAllLesson() throws LessonBusinessException {

        List<Lesson> lessons = new ArrayList<Lesson>();
        try {
            lessons = new ArrayList<>(simulateFindAllLesson());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nel prendere la lista di lezioni");
        }
        return lessons;

    }*/

    @Override
    public List<Lesson> findAllLessonByTeacher(Teacher teacher) throws LessonBusinessException {
        LessonDao lessonDao = new LessonDao();
        try {
            return lessonDao.getLessonsByTeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nel prendere la lista di lezioni");
        }
    }

    @Override
    public void updateLessson(Lesson lesson) throws SubjectBusinessException, LessonBusinessException {
        LessonDaoInterface lessonDao = new LessonDao();
        SubjectDaoInterface subjectDao = new SubjectDao();

        try {
            subjectDao.modifySubjectByID(lesson.getSubject());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore nella modifica della materia nella modifica lezione");
        }

        try {
            lessonDao.modifyLesson(lesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nella modifica della lezione");
        }
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
}

