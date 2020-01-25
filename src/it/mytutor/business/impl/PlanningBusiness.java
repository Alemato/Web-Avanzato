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
        List<Planning> planningList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();
        Subject subject = new Subject();
        boolean existSubject = false;
        int i;

        try {
            subjectList = subjectDao.getAllSubject();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SubjectBusinessException("Errore nel prendere i Subject dal database");
        }

        for (Subject subject1 : subjectList) {
            if (plannings.get(0).getLesson().getSubject().getMicroSubject().equals(subject1.getMicroSubject())) {
                subject = subject1;
                existSubject = true;
                break;
            }
        }
        if (!existSubject) {
            try {
                subjectDao.createSubject(plannings.get(0).getLesson().getSubject());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new SubjectBusinessException("Errore nel'aggiunta del subject nel database");
            }
        }
        Lesson lesson = plannings.get(0).getLesson();
        lesson.setTeacher(teacher);
        if (!existSubject) {
            subject = plannings.get(0).getLesson().getSubject();

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
            Date dateAppo = new Date(planning.getDate().getTime());
            Date dateAddWeek = new Date(planning.getDate().getTime());
            planningList.add(planning);

            while (dateAddWeek.before(addYearToJavaUtilDate(dateAppo))) {
                dateAddWeek = addWeekToJavaUtilDate(dateAddWeek);
                planningList.add(new Planning(planning.getIdPlanning(), new java.sql.Date(dateAddWeek.getTime()),
                        planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(),
                        planning.getUpdateDate(), planning.getLesson()));
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
                            planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(),
                            planning.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning.getIdPlanning(), planning.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"), planning.getAvailable(),
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
    public void deletePlanningsByLesson(Lesson lesson) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
        try {
            planningDao.deletePlanningsByLesson(lesson);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nell'aggiunta dei plannings");
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
            Date dateAppo = new Date(planning.getDate().getTime());
            Date dateAddWeek = new Date(planning.getDate().getTime());
            planningList.add(planning);

            while (dateAddWeek.before(addYearToJavaUtilDate(dateAppo))) {
                dateAddWeek = addWeekToJavaUtilDate(dateAddWeek);
                planningList.add(new Planning(planning.getIdPlanning(), new java.sql.Date(dateAddWeek.getTime()), planning.getStartTime(),
                        planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
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
                            planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                    if (dateStart > 82800000) {
                        planningsOrari.set(planningsOrari.size() - 1, new Planning(planning.getIdPlanning(), planning.getDate(),
                                Time.valueOf("23:00:00"), Time.valueOf("23:59:59"), planning.getAvailable(),
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
    public List<Planning> findPlanningByFilter(String macroMateria, String nome, String zona, String microMateria,
                                               String dom, String lun, String mar, String mer, String gio, String ven, String sab, String prezzo, String oraInizio, String oraFine) throws PlanningBusinessException {
        PlanningDaoInterface planningDao = new PlanningDao();
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
            plannings = planningDao.getPlanningByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome, zonaRelevant, zona, microMateriaRelevant, microMateria, prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);


        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere la lista dei planning");
        }
        if (dom != null && !dom.isEmpty() && !dom.equals("0") || lun != null && !lun.isEmpty() && !lun.equals("0") ||
                mar != null && !mar.isEmpty() && !mar.equals("0") || mer != null && !mer.isEmpty() && !mer.equals("0") ||
                gio != null && !gio.isEmpty() && !gio.equals("0") || ven != null && !ven.isEmpty() && !ven.equals("0") ||
                sab != null && !sab.isEmpty() && !sab.equals("0")) {
            plannings = dayOfWeek(plannings, dom, lun, mar, mer, gio, ven, sab);
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

    private ArrayList<Planning> dayOfWeek(List<Planning> plannings, String dom, String lun, String mar, String mer, String gio, String ven, String sab) {
        Calendar c = Calendar.getInstance();
        ArrayList<Planning> plannings1 = new ArrayList<>();
        if (dom != null && !dom.equals("") && dom.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (lun != null && !lun.equals("") && lun.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (mar != null && !mar.equals("") && mar.equals("1")) {
            System.out.println("mar");
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                System.out.println(c.get(Calendar.DAY_OF_WEEK));
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (mer != null && !mer.equals("") && mer.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (gio != null && !gio.equals("") && gio.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (ven != null && !ven.equals("") && ven.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        if (sab != null && !sab.equals("") && sab.equals("1")) {
            for (Planning planning : plannings) {
                c.setTime(planning.getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    plannings1.add(new Planning(planning.getIdPlanning(), planning.getDate(), planning.getStartTime(), planning.getEndTime(), planning.getAvailable(), planning.getCreateDate(), planning.getUpdateDate(), planning.getLesson()));
                }
            }
        }
        return plannings1;
    }

}
