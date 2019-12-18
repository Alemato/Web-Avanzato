package it.mytutor.business.impl;

import it.mytutor.business.services.PlanningInterface;
import it.mytutor.domain.Planning;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.PlanningDao;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class PlanningBusiness implements PlanningInterface {

    @Override
    public void CreaPlanning(List<Planning> plannings) {
        PlanningDaoInterface planningDaoInterface = new PlanningDao();
        for (Planning planning: plannings) {
            try {
                planningDaoInterface.createPlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Planning> FindPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                               String giornoSettimana, String prezzo, String oraInizio, String oraFine) {
        PlanningDaoInterface planningDaoInterface = new PlanningDao();

        List<Planning> plannings = new ArrayList<>();
        int macroMateriaRelevant = 0;
        if (macroMateria != null && !macroMateria.isEmpty()) {
            macroMateriaRelevant = 1;
        }
        int nomeRelevant = 0;
        if (nome != null && !nome.isEmpty()) {
            nomeRelevant = 1;
        }
        int zonaRelevant = 0;
        if (zona != null && !zona.isEmpty()) {
            zonaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.isEmpty()) {
            microMateriaRelevant = 1;
        }
        int giornoSettimanaRelevant = 0;
        if (giornoSettimana != null && !giornoSettimana.isEmpty()) {
            giornoSettimanaRelevant = 1;
        }
        int prezzoRelevant = 0;
        if (prezzo != null && !prezzo.isEmpty()) {
            prezzoRelevant = 1;
        }
        int oraInizioRelevant = 0;
        if (oraInizio != null && !oraInizio.isEmpty()) {
            oraInizioRelevant = 1;
        }
        int oraFineaRelevant = 0;
        if (oraFine != null && !oraFine.isEmpty()) {
            oraFineaRelevant = 1;
        }

        try {
            plannings = planningDaoInterface.getPlanningByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome,
                    zonaRelevant, zona, microMateriaRelevant, microMateria, giornoSettimanaRelevant, giornoSettimana,
                    prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return plannings;
    }
}
