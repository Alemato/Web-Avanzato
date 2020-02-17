package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface PlanningDaoInterface {
    List<Planning> getPlanningByTeacher(Teacher teacher) throws DatabaseException;

    List<Planning> getPlanningByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant, String nome,
                                       int zonaRelevant, String zona, int microMateriaRelevant, String microMateria, int prezzoRelevant,
                                       String prezzo, int oraInizioRelevant, String oraInizio,
                                       int oraFineaRelevant, String oraFine) throws DatabaseException;

    List<Planning> getPlanningByLessonId(Integer idLesson) throws DatabaseException;

    List<Planning> getPlanningBookedUpByLessonId(Integer idLesson, Integer idStudent) throws DatabaseException;

    void updatePlanning(Planning planning) throws DatabaseException;
    void createPlanning(Planning planning) throws DatabaseException;

    void addPlanning(Planning planning) throws DatabaseException;

    void deletePlanningsByLesson(Integer idLesson) throws DatabaseException;

    void deletePlanning(Integer idFirst, Integer idLast) throws DatabaseException;
}
