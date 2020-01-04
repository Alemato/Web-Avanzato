package it.mytutor.business.services;

import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface PlanningInterface {
    void creaPlanning(List<Planning> plannings, Teacher teacher) throws PlanningBusinessException, LessonBusinessException, SubjectBusinessException;

    void deletePlannings(List<Planning> plannings) throws PlanningBusinessException;

    void addPlannings(List<Planning> plannings) throws PlanningBusinessException;

    void updatePlanning(List<Planning> plannings) throws PlanningBusinessException;

    List<Planning> FindPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                        String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException, BookingBusinessException, PlanningBusinessException;
}
