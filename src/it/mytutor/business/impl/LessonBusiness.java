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
    public List<Lesson> findAllLessonByTeacher(Teacher teacher) {

        List<Lesson> lessons = new ArrayList<>();

        return lessons;
    }

    @Override
    public List<Lesson> findAllLessonBySubject(Subject subject) {
        return null;
    }

//    @Override
//    public List<Lesson> findLessonByFilter(String macroMateria, String nome, String zona, String microMateria,
//                                           String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException {
//        LessonDao lessonDao = new LessonDao();
//        List<Lesson> lessons = new ArrayList<>();
//        int macroMateriaRelevant  = 0;
//        if (macroMateria != null && !macroMateria.isEmpty()){
//            macroMateriaRelevant = 1;
//        }
//        int nomeRelevant  = 0;
//        if (nome != null && !nome.isEmpty()){
//            nomeRelevant = 1;
//        }
//        int zonaRelevant  = 0;
//        if (zona != null && !zona.isEmpty()){
//            zonaRelevant = 1;
//        }
//        int microMateriaRelevant  = 0;
//        if (microMateria != null && !microMateria.isEmpty()){
//            microMateriaRelevant = 1;
//        }
//        int giornoSettimanaRelevant  = 0;
//        if (giornoSettimana != null && !giornoSettimana.isEmpty()){
//            giornoSettimanaRelevant = 1;
//        }
//        int prezzoRelevant  = 0;
//        if (prezzo != null && !prezzo.isEmpty()){
//            prezzoRelevant = 1;
//        }
//        int oraInizioRelevant  = 0;
//        if (oraInizio != null && !oraInizio.isEmpty()){
//            oraInizioRelevant = 1;
//        }
//        int oraFineaRelevant  = 0;
//        if (oraFine != null && !oraFine.isEmpty()){
//            oraFineaRelevant = 1;
//        }
//
//        lessons = lessonDao.getLessonByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome,
//                zonaRelevant, zona, microMateriaRelevant, microMateria, giornoSettimanaRelevant, giornoSettimana,
//                prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);
//        return lessons;
//    }
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

