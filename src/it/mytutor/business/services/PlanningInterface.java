package it.mytutor.business.services;

import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface PlanningInterface {
    void creaPlanning(Planning planning, Teacher teacher) throws PlanningBusinessException, LessonBusinessException, SubjectBusinessException;

    void deletePlanningsByLesson(Integer idLesson) throws PlanningBusinessException;

    void deletePlannings(List<Planning> plannings) throws PlanningBusinessException;

    void addPlannings(List<Planning> plannings) throws PlanningBusinessException;

    void updatePlanning(List<Planning> plannings) throws PlanningBusinessException;

    List<Planning> findPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                        String dom, String lun, String mar, String mer, String gio, String ven, String sab,  String prezzo, String oraInizio, String oraFine) throws DatabaseException, BookingBusinessException, PlanningBusinessException;

    List<Planning> findAllPlanningByLessonId(Integer idLesson) throws PlanningBusinessException;

    List<Planning> findAllPlanningBookedUpByLessonId(Integer idLesson, Integer idStudent) throws PlanningBusinessException;

    List<Planning> findPlanningsOfATeacherAsLesson(Teacher teacher) throws PlanningBusinessException;

    Planning findPlanningById(Integer idPlanning) throws PlanningBusinessException;
}
