package it.mytutor.business.impl;

import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.services.PlanningInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.PlanningDao;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlanningBusiness implements PlanningInterface {

    @Override
    public void creaPlanning(Planning planning, Teacher teacher) throws PlanningBusinessException {
        PlanningDao planningDao = new PlanningDao();
        List<Planning> planningList = new ArrayList<>();
        List<Planning> planningsOrari = new ArrayList<>();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        boolean check = false;
        long cost = 3600000;

        if (planning.getRepeatPlanning()) {
            Date dateAppo = new Date(planning.getDate().getTime());
            Date dateAddWeek = new Date(planning.getDate().getTime());
            planningList.add(planning);

            while (dateAddWeek.before(addYearToJavaUtilDate(dateAppo))) {
                dateAddWeek = addWeekToJavaUtilDate(dateAddWeek);
                planningList.add(new Planning(planning.getIdPlanning(), new java.sql.Date(dateAddWeek.getTime()),
                        planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(),
                        planning.getUpdateDate(), planning.getLesson()));
            }
        } else {
            planningList.add(planning);
        }

        for (Planning planning1 : planningList) {
            Date startDate = new Date(planning1.getStartTime().getTime());
            Date endDate = new Date(planning1.getEndTime().getTime());
            Date dateZero = new Date(Time.valueOf("00:00:00").getTime() + cost);

            if (endDate.before(addHoursToJavaUtilDate(startDate))) {
                throw new PlanningBusinessException("Errore nell'immissione della data");
            } else {
                while (addHoursToJavaUtilDate(startDate).before(endDate)) {
                    long dateStart = startDate.getTime() + dateZero.getTime();
                    startDate = new Date(dateStart);
                    dateZero = addHoursToJavaUtilDate(dateZero);
                    check = true;
                    planningsOrari.add(new Planning(planning1.getIdPlanning(), planning1.getDate(),
                            Time.valueOf(localDateFormat.format(startDate)),
                            Time.valueOf(localDateFormat.format(addHoursToJavaUtilDate(new Date(dateStart)))),
                            planning1.getAvailable(), planning1.getRepeatPlanning(), planning1.getCreateDate(), planning1.getUpdateDate(),
                            planning1.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning1.getIdPlanning(), planning1.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"), planning1.getAvailable(), planning1.getRepeatPlanning(),
                                planning1.getCreateDate(), planning1.getUpdateDate(), planning1.getLesson()));
                    }
                }
                if (!check) {
                    planningsOrari.add(planning1);
                }
            }
        }

        for (Planning planning1 : planningsOrari) {
            planning1.setAvailable(true);
            try {

                planningDao.createPlanning(planning1);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nel'aggiunta del planning nel database");
            }
        }
    }

    @Override
    public void deletePlanningsByLesson(Integer idLesson) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        try {
            planningDao.deletePlanningsByLesson(idLesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
        }
    }

    @Override
    public void deletePlannings(List<Planning> plannings) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        if (plannings.size() == 1) {
            try {
                planningDao.deletePlanning(plannings.get(0).getIdPlanning(), plannings.get(0).getIdPlanning());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
            }
        } else {
            try {
                planningDao.deletePlanning(plannings.get(0).getIdPlanning(), plannings.get(plannings.size() - 1).getIdPlanning());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
            }
        }
    }

    private Date addHoursToJavaUtilDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

    private Date addWeekToJavaUtilDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return calendar.getTime();
    }

    private Date addYearToJavaUtilDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }


    @Override
    public void addPlannings(List<Planning> plannings) throws PlanningBusinessException {
        List<Planning> planningsOrari = new ArrayList<>();
        PlanningDaoInterface planningDao = new PlanningDao();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        List<Planning> planningList = new ArrayList<>();
        boolean check = false;
        long cost = 3600000;

        for (Planning planning : plannings) {
            if (planning.getRepeatPlanning()) {
                Date dateAppo = new Date(planning.getDate().getTime());
                Date dateAddWeek = new Date(planning.getDate().getTime());
                planningList.add(planning);

                while (dateAddWeek.before(addYearToJavaUtilDate(dateAppo))) {
                    dateAddWeek = addWeekToJavaUtilDate(dateAddWeek);
                    planningList.add(new Planning(planning.getIdPlanning(), new java.sql.Date(dateAddWeek.getTime()), planning.getStartTime(),
                            planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            } else {
                planningList.add(planning);
            }
        }

        for (Planning planning : planningList) {
            Date startDate = new Date(planning.getStartTime().getTime());
            Date endDate = new Date(planning.getEndTime().getTime());
            Date dateZero = new Date(Time.valueOf("00:00:00").getTime() + cost);

            if (endDate.before(addHoursToJavaUtilDate(startDate))) {
                throw new PlanningBusinessException("Errore nell'immissione della data");
            } else {
                while (addHoursToJavaUtilDate(startDate).before(endDate)) {
                    long dateStart = startDate.getTime() + dateZero.getTime();
                    startDate = new Date(dateStart);
                    dateZero = addHoursToJavaUtilDate(dateZero);
                    check = true;
                    planningsOrari.add(new Planning(planning.getIdPlanning(), planning.getDate(),
                            Time.valueOf(localDateFormat.format(startDate)),
                            Time.valueOf(localDateFormat.format(addHoursToJavaUtilDate(new Date(dateStart)))),
                            planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning.getIdPlanning(), planning.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"), planning.getAvailable(), planning.getRepeatPlanning(),
                                planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    }
                }
                if (!check) {
                    planningsOrari.add(planning);
                }
            }
        }

        for (Planning planning : planningsOrari) {
            planning.setAvailable(true);
            try {
                planningDao.addPlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
            }
        }
    }

    @Override
    public void updatePlanning(List<Planning> plannings) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        List<Planning> planningList = new ArrayList<>();
        if (plannings.size() > 1) {
            try {
                planningDao.deletePlanning(plannings.get(0).getIdPlanning(), plannings.get(plannings.size() - 1).getIdPlanning());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nella cancellazione dei planning");
            }
            planningList.add(plannings.get(0));
            addPlannings(planningList);
        } else {
            for (Planning planning : plannings) {

                try {
                    planningDao.updatePlanning(planning);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new PlanningBusinessException("Errore nella modifica del planning");
                }
            }
        }
    }

    @Override
    public List<Planning> findPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                               String dom, String lun, String mar, String mer, String gio, String ven, String sab, String prezzo, String oraInizio, String oraFine) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        List<Planning> plannings;
        List<Planning> pFinali = new ArrayList<>();
        int macroMateriaRelevant = 0;
        if (macroMateria != null && !macroMateria.equals("null") && !macroMateria.isEmpty() && !macroMateria.equals(" ")) {
            macroMateriaRelevant = 1;
        }
        int nomeRelevant = 0;
        if (nome != null && !nome.equals("null") && !nome.isEmpty() && !nome.equals(" ")) {
            nomeRelevant = 1;
        }
        int zonaRelevant = 0;
        if (zona != null && !zona.equals("null") && !zona.isEmpty() && !zona.equals(" ")) {
            zonaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.equals("null") && !microMateria.isEmpty() && !microMateria.equals(" ")) {
            microMateriaRelevant = 1;
        }
        int prezzoRelevant = 0;
        if (prezzo != null && !prezzo.equals("null") && !prezzo.isEmpty() && !prezzo.equals(" ")) {
            prezzoRelevant = 1;
        }
        int oraInizioRelevant = 0;
        if (oraInizio != null && !oraInizio.equals("null") && !oraInizio.isEmpty() && !oraInizio.equals(" ")) {
            oraInizioRelevant = 1;
        }
        int oraFineaRelevant = 0;
        if (oraFine != null && !oraFine.equals("null") && !oraFine.isEmpty() && !oraFine.equals(" ")) {
            oraFineaRelevant = 1;
        }

        try {
            plannings = planningDao.getPlanningByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome, zonaRelevant, zona, microMateriaRelevant, microMateria, prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);


        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }
        if (dom != null && !dom.isEmpty() && !dom.equals(" ") && !dom.equals("0") || lun != null && !lun.isEmpty() && !lun.equals(" ") && !lun.equals("0") ||
                mar != null && !mar.isEmpty() && !mar.equals(" ") && !mar.equals("0") || mer != null && !mer.isEmpty() && !mer.equals(" ") && !mer.equals("0") ||
                gio != null && !gio.isEmpty() && !gio.equals(" ") && !gio.equals("0") || ven != null && !ven.isEmpty() && !ven.equals(" ") && !ven.equals("0") ||
                sab != null && !sab.isEmpty() && !sab.equals(" ") && !sab.equals("0")) {
            plannings = dayOfWeek(plannings, dom, lun, mar, mer, gio, ven, sab);
        }

        for (Planning p : plannings) {
            if (p.getDate().getTime() > new java.sql.Date(System.currentTimeMillis()).getTime()) {
                pFinali.add(p);
            } else {
                PlanningDaoInterface planningDaoRep = new PlanningDao();
                p.setAvailable(false);
                try {
                    planningDaoRep.updatePlanning(p);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new PlanningBusinessException("Errore nel'aggiornare l'oggetto plenning");
                }
            }
        }
        return pFinali;
    }

    @Override
    public List<Planning> findAllPlanningByLessonId(Integer idLesson) throws PlanningBusinessException {
        PlanningDao planningDao = new PlanningDao();
        List<Planning> plannings;
        List<Planning> pFinali = new ArrayList<>();
        try {
            plannings = planningDao.getPlanningByLessonId(idLesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }

        for (Planning p : plannings) {
            if (p.getDate().getTime() > new java.sql.Date(System.currentTimeMillis()).getTime()) {
                pFinali.add(p);
            } else {
                PlanningDaoInterface planningDaoRep = new PlanningDao();
                p.setAvailable(false);
                try {
                    planningDaoRep.updatePlanning(p);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new PlanningBusinessException("Errore nel'aggiornare l'oggetto plenning");
                }
            }
        }
        return pFinali;
    }

    @Override
    public List<Planning> findAllPlanningBookedUpByLessonId(Integer idLesson, Integer idStudent) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        List<Planning> plannings;
        try {
            plannings = planningDao.getPlanningBookedUpByLessonId(idLesson, idStudent);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }
        return plannings;
    }

    private ArrayList<Planning> dayOfWeek(List<Planning> plannings, String dom, String lun, String mar, String mer, String gio, String ven, String sab) {
        Calendar c = Calendar.getInstance();
        ArrayList<Planning> plannings1 = new ArrayList<>();
        if (dom != null && !dom.equals("") && dom.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (lun != null && !lun.equals("") && lun.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (mar != null && !mar.equals("") && mar.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (mer != null && !mer.equals("") && mer.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (gio != null && !gio.equals("") && gio.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (ven != null && !ven.equals("") && ven.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (sab != null && !sab.equals("") && sab.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getRepeatPlanning(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        return plannings1;
    }

    @Override
    public List<Planning> findPlanningsOfATeacherAsLesson(Teacher teacher) throws PlanningBusinessException {
        PlanningDao planningDao = new PlanningDao();
        List<Planning> plannings;
        HashMap<Integer, Planning> hashMap = new HashMap<>();
        List<Planning> pFinali = new ArrayList<>();
        List<Planning> pFinaliAppo = new ArrayList<>();
        HashSet<Lesson> lessons = new HashSet<>();
        Planning planningAppo = new Planning();

        try {
            plannings = planningDao.getPlanningByTeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }

        for (Planning p : plannings) {
            lessons.add(p.getLesson());
            if (p.getDate().getTime() > new java.sql.Date(System.currentTimeMillis()).getTime()) {
                pFinali.add(p);
                if (p.getAvailable()) {
                    pFinaliAppo.add(p);
                }
            }
            else {
                PlanningDaoInterface planningDaoRep = new PlanningDao();
                p.setAvailable(false);
                try {
                    planningDaoRep.updatePlanning(p);
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new PlanningBusinessException("Errore nel'aggiornare l'oggetto plenning");
                }
            }
        }

        for (int i = pFinaliAppo.size() - 1; i >= 0; i--) {
            hashMap.put(pFinaliAppo.get(i).getLesson().getIdLesson(), pFinaliAppo.get(i));
        }
        boolean flag = false;
        for (Lesson lesson: lessons) {
            for (Planning planning: pFinali) {
               planningAppo = planning;
                if (planning.getLesson().getIdLesson().equals(lesson.getIdLesson())) {
                    flag = true;
                }
            }
            if (!flag) {
                planningAppo.setAvailable(false);
                planningAppo.setLesson(lesson);
                hashMap.put(lesson.getIdLesson(), planningAppo);
            }
        }

        return new ArrayList<>(hashMap.values());
    }

    @Override
    public Planning findPlanningById(Integer idPlanning) throws PlanningBusinessException {
        PlanningDao planningDao = new PlanningDao();
        Planning planning;
        try {
            planning = planningDao.getPlanningById(idPlanning);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }


        return planning;
    }

}
