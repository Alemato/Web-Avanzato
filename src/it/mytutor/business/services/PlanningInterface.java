package it.mytutor.business.services;

import it.mytutor.domain.Planning;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface PlanningInterface {
    void CreaPlanning(List<Planning> plannings);

    List<Planning> FindPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                       String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException;
}
