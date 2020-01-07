package it.mytutor.business.impl;

import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.services.PlanningInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.LessonDao;
import it.mytutor.domain.dao.implement.PlanningDao;
import it.mytutor.domain.dao.implement.SubjectDao;
import it.mytutor.domain.dao.interfaces.LessonDaoInterface;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;
import it.mytutor.domain.dao.interfaces.SubjectDaoInterface;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanningBusiness implements PlanningInterface {

    @Override
    public void creaPlanning(List<Planning> plannings, Teacher teacher) throws PlanningBusinessException, LessonBusinessException, SubjectBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        LessonDaoInterface lessonDao = new LessonDao();
        SubjectDaoInterface subjectDao = new SubjectDao();
        int i;

        try {
            subjectDao.createSubject(plannings.get(0).getLesson().getSubject());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore nel'aggiunta del subject nel database");
        }
        Lesson lesson = plannings.get(0).getLesson();
        lesson.setTeacher(teacher);

        Subject subject = plannings.get(0).getLesson().getSubject();
        List<Subject> subjects;

        try {
            subjects = subjectDao.getSubjectsByName(plannings.get(0).getLesson().getSubject().getMacroSubject());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore nel prendere gli oggetti subject dal database");
        }
        for (Subject subject1 : subjects) {
            if (subject1.getMicroSubject().equals(plannings.get(0).getLesson().getSubject().getMicroSubject())) {
                subject.setIdSubject(subject1.getIdSubject());
                break;
            }
        }
        lesson.setSubject(subject);
        try {
            i = lessonDao.createLesson(lesson);
            lesson.setIdLesson(i);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new LessonBusinessException("Errore nel'aggiunta della Lesson nel database");
        }
        List<Planning> planningsOrari = new ArrayList<>();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");

        boolean check = false;
        long cost = 3600000;

        for (Planning planning : plannings) {
            java.util.Date startDate = new java.util.Date(planning.getStartTime().getTime());
            java.util.Date endDate = new java.util.Date(planning.getEndTime().getTime());
            java.util.Date dateZero = new java.util.Date(Time.valueOf("00:00:00").getTime() + cost);

            if (endDate.before(addHoursToJavaUtilDate(startDate))) {
                throw new PlanningBusinessException("Errore nell'immissione della data");
            } else {
                while (addHoursToJavaUtilDate(startDate).before(endDate)) {
                    long dateStart = startDate.getTime() + dateZero.getTime();
                    startDate = new Date(dateStart);
                    dateZero = addHoursToJavaUtilDate(dateZero);
                    check = true;
                    planningsOrari.add(new Planning(planning.getIdPlanning(), planning.getDate(),
                            Time.valueOf(localDateFormat.format(startDate)), Time.valueOf(localDateFormat.format(addHoursToJavaUtilDate(new Date(dateStart)))),
                            planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning.getIdPlanning(), planning.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"),
                                planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    }
                }
                if (!check) {
                    planningsOrari.add(planning);
                }
            }
        }

        for (Planning planning : planningsOrari) {
            planning.setLesson(lesson);
            try {
                planningDao.createPlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nel'aggiunta del planning nel database");
            }
        }
    }

    @Override
    public void deletePlannings(List<Planning> plannings) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        for (Planning planning : plannings) {
            try {
                planningDao.deletePlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
            }
        }
    }

    private java.util.Date addHoursToJavaUtilDate(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

//    private java.util.Date subtractHoursToJavaUtilDate(java.util.Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.HOUR_OF_DAY, -1);
//        return calendar.getTime();
//    }


    @Override
    public void addPlannings(List<Planning> plannings) throws PlanningBusinessException {
        List<Planning> planningsOrari = new ArrayList<>();
        PlanningDaoInterface planningDao = new PlanningDao();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        boolean check = false;
        long cost = 3600000;

        for (Planning planning : plannings) {
            java.util.Date startDate = new java.util.Date(planning.getStartTime().getTime());
            java.util.Date endDate = new java.util.Date(planning.getEndTime().getTime());
            java.util.Date dateZero = new java.util.Date(Time.valueOf("00:00:00").getTime() + cost);

            if (endDate.before(addHoursToJavaUtilDate(startDate))) {
                throw new PlanningBusinessException("Errore nell'immissione della data");
            } else {
                while (addHoursToJavaUtilDate(startDate).before(endDate)) {
                    long dateStart = startDate.getTime() + dateZero.getTime();
                    startDate = new Date(dateStart);
                    dateZero = addHoursToJavaUtilDate(dateZero);
                    check = true;
                    planningsOrari.add(new Planning(planning.getIdPlanning(), planning.getDate(),
                            Time.valueOf(localDateFormat.format(startDate)), Time.valueOf(localDateFormat.format(addHoursToJavaUtilDate(new Date(dateStart)))),
                            planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning.getIdPlanning(), planning.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"),
                                planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    }
                }
                if (!check) {
                    planningsOrari.add(planning);
                }
            }
        }

        for (Planning planning : planningsOrari) {

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
        for (Planning planning : plannings) {
            try {
                planningDao.updatePlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new PlanningBusinessException("Errore nella modifica del planning");
            }
        }
    }

    @Override
    public List<Planning> FindPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                               String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws PlanningBusinessException {
        PlanningDaoInterface planningDaoInterface = new PlanningDao();

        List<Planning> plannings;
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
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }
        return plannings;
    }

    @Override
    public List<Planning> findAllPlanningByLessonId(Integer idLesson) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        List<Planning> plannings;
        try {
            plannings = planningDao.getPlanningByLessonId(idLesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }
        return plannings;
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

}

/*    long cost = 3600000;
    Time unOra = Time.valueOf("01:00:00");
    Date date = new Date(unOra.getTime());
    List<Planning> planningsOrari = new ArrayList<>();
        for (Planning planning: plannings) {
                System.out.println((planning.getEndTime().getTime() + cost) / (60 * 60 * 1000));
                System.out.println((planning.getStartTime().getTime() + cost) / (60 * 60 * 1000));
                System.out.println(((planning.getEndTime().getTime()+ cost) - (planning.getStartTime().getTime()+ cost))/ (60 * 60 * 1000));
                if ((planning.getEndTime().getTime() + cost) - (planning.getStartTime().getTime() + cost) >= 0) {
                if ((((planning.getEndTime().getTime() + cost) - (planning.getStartTime().getTime()) + cost) / (60 * 60 * 1000)) > 1) {
                Time timeAppo = Time.valueOf("02:00:00");
                System.out.println("TimeAppo 01:00:00");
                System.out.println(timeAppo.getTime());
                Time timeAppo1 = Time.valueOf("01:00:00");
                System.out.println("TimeAppo1 00:00:00");
                System.out.println(timeAppo1.getTime());

                while (((planning.getStartTime().getTime()) + timeAppo.getTime()) < (planning.getEndTime().getTime())){
        System.out.println("setStartTime");
        System.out.println(new Time((planning.getStartTime().getTime() + cost) + timeAppo1.getTime() + cost));
        planning.setStartTime(new Time((planning.getStartTime().getTime() + cost) + (timeAppo1.getTime() + cost)));
        timeAppo1 = new Time((timeAppo1.getTime() + cost) + (unOra.getTime() + cost));
        System.out.println("setEndTime");
        System.out.println(new Time((planning.getStartTime().getTime() + cost) + (unOra.getTime() + cost)));
        planning.setEndTime(new Time((planning.getStartTime().getTime() + cost) + (unOra.getTime() + cost)));
        planningsOrari.add(planning);
        timeAppo = new Time((timeAppo.getTime() + cost) + (unOra.getTime() + cost));

        }
        planning.setStartTime(new Time((timeAppo1.getTime())));

        planningsOrari.add(planning);
        } else {
        planningsOrari.add(planning);
        }

        } else {
        throw new PlanningBusinessException("Errore nell'inserimento delle date");
        }
        }*/
