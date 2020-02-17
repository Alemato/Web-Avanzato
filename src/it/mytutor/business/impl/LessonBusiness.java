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
import it.mytutor.domain.dao.implement.PlanningDao;
import it.mytutor.domain.dao.implement.SubjectDao;
import it.mytutor.domain.dao.interfaces.LessonDaoInterface;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class LessonBusiness implements LessonInterface {

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
        List<Subject> subjectList;

        Subject subject = new Subject();

        boolean existSubject = false;

        try {
            subjectList = subjectDao.getAllSubject();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore nel prendere i Subject dal database");
        }

        for (Subject subject1 : subjectList) {
            if (lesson.getSubject().getMicroSubject().equals(subject1.getMicroSubject()) &&
                    lesson.getSubject().getMacroSubject().equals(subject1.getMacroSubject())) {
                subject = subject1;
                existSubject = true;
                break;
            }
        }
        if (!existSubject) {
            try {
                subjectDao.createSubject(lesson.getSubject());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new SubjectBusinessException("Errore nel'aggiunta del subject nel database");
            }
            subject = lesson.getSubject();

            List<Subject> subjects;

            try {
                subjects = subjectDao.getSubjectsByName(lesson.getSubject().getMacroSubject());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new SubjectBusinessException("Errore nel prendere gli oggetti subject dal database");
            }
            for (Subject subject1 : subjects) {
                if (subject1.getMicroSubject().equals(lesson.getSubject().getMicroSubject())) {
                    subject.setIdSubject(subject1.getIdSubject());
                    break;
                }
            }
        }
        lesson.setSubject(subject);


        try {
            lessonDao.modifyLesson(lesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nella modifica della lezione");
        }
    }

    @Override
    public List<Lesson> findlessonWithoutPlanningByTeacher(Teacher teacher) throws LessonBusinessException {
        List<Lesson> lessons;
        LessonDao lessonDao = new LessonDao();
        try {
            lessons = lessonDao.getLessonWithoutPlanningByTeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nel prendere la lista di lezioni");
        }
        return lessons;
    }

    @Override
    public List<Lesson> findAllLessonBySubject(Subject subject) {
        return null;
    }

    @Override
    public Lesson findLessonByID(Integer idLesson) throws LessonBusinessException {
        LessonDao lessonDao = new LessonDao();
        Lesson lesson = new Lesson();
        try {
            lesson = lessonDao.getLessonById(idLesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nella modifica della lezione");
        }
        return lesson;
    }

    @Override
    public Integer createLesson(Lesson lesson) throws LessonBusinessException {
        boolean subjectTrovata = false;
        int idLesson;
        int idSubject;
        List<Subject> subjects;
        LessonDao lessonDao = new LessonDao();
        SubjectDao subjectDao = new SubjectDao();

        try {
            subjects = subjectDao.getSubjectsByName(lesson.getSubject().getMacroSubject());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nella prendere le materie");
        }
        if (!subjects.isEmpty()) {
            for (Subject subject: subjects) {
                if (subject.getMicroSubject().equals(lesson.getSubject().getMicroSubject())) {
                    lesson.getSubject().setIdSubject(subject.getIdSubject());
                    subjectTrovata = true;
                    break;
                }
            }
            if (!subjectTrovata) {
                try {
                    idSubject = subjectDao.createSubject(lesson.getSubject());
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new LessonBusinessException("Errore nella creazione della materia");
                }
                lesson.getSubject().setIdSubject(idSubject);
            }
        } else {
            try {
                idSubject = subjectDao.createSubject(lesson.getSubject());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new LessonBusinessException("Errore nella creazione della materia");
            }
            lesson.getSubject().setIdSubject(idSubject);
        }

        try {
            idLesson = lessonDao.createLesson(lesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nella creazione della lezione");
        }
        return idLesson;
    }
}

