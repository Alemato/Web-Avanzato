package it.mytutor.business.impl;

import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.*;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.*;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;
import it.mytutor.domain.dao.interfaces.PlanningDaoInterface;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookingBusiness implements BookingInterface {


    public List<Booking> findBookingByFilter(String macroMateria, String nome, String zona, String microMateria, String dom, String lun, String mar, String mer, String gio, String ven, String sab, String prezzo, String oraInizio, String oraFine) throws PlanningBusinessException, BookingBusinessException {
        BookingDaoInterface bookingDao = new BookingDao();
        List<Booking> bookings;
        List<Booking> bFinali = new ArrayList<>();
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
            bookings = bookingDao.getBookingByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome,
                    zonaRelevant, zona, microMateriaRelevant, microMateria, prezzoRelevant, prezzo, oraInizioRelevant,
                    oraInizio, oraFineaRelevant, oraFine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere gli oggetti booking");
        }

        List<Lesson> lessons = new ArrayList<>();
        for (Booking booking : bookings){
            lessons.add(booking.getPlanning().getLesson());
        }
        Set<Lesson> s = new HashSet<>(lessons);
        lessons = new ArrayList<>(s);

        List<Booking> bookings1 = new ArrayList<>();
        for (Lesson lesson: lessons) {
            try {
                bookings1.addAll(bookingDao.getAllBookingByIdLesson(lesson.getIdLesson()));
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
            }
        }
        if (dom != null && !dom.isEmpty() && !dom.equals("0") || lun != null && !lun.isEmpty() && !lun.equals("0") ||
                mar != null && !mar.isEmpty() && !mar.equals("0") || mer != null && !mer.isEmpty() && !mer.equals("0") ||
                gio != null && !gio.isEmpty() && !gio.equals("0") || ven != null && !ven.isEmpty() && !ven.equals("0") ||
                sab != null && !sab.isEmpty() && !sab.equals("0") ) {
            bookings1 = dayOfWeek(bookings1, dom, lun, mar, mer, gio, ven, sab);
        }
        for (Booking b: bookings1) {
            if (b.getPlanning().getDate().getTime() > new Date(System.currentTimeMillis()).getTime()) {
                bFinali.add(b);
            } else {
                PlanningDaoInterface planningDao = new PlanningDao();
                b.getPlanning().setAvailable(false);
                try {
                    planningDao.updatePlanning(b.getPlanning());
                } catch (DatabaseException e) {
                    e.printStackTrace();
                    throw new BookingBusinessException("Errore nel'aggiornare l'oggetto plenning");
                }
            }
        }
        return bFinali;
    }

    @Override
    public List<Booking> findAllbookedUpByStudent(Student student) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;
        try {
            bookings = bookingDao.findAllbookedUpByStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'oggetto booking");
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllbookedUpByTeacher(Teacher teacher) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;
        try {
            bookings = bookingDao.findAllbookedUpByTeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'oggetto booking");
        }
        return bookings;
    }



    @Override
    public Booking findBookingById(Integer idBooking) throws BookingBusinessException {
        BookingDaoInterface bookingDao = new BookingDao();
        Booking booking;
        try {
            booking = bookingDao.getBookingById(idBooking);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'oggetto booking");
        }
        return booking;
    }

    @Override
    public List<Booking> findAllBookingByStudnet(Student student) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getAllBookingOfAStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByTeacher(Teacher teacher) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getAllBookingOfATeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }
        return bookings;
    }

    @Override
    public int crateBooking(Booking booking) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        PlanningDao planningDao = new PlanningDao();
        Planning planning;
        int idBoking;
        try {
            idBoking = bookingDao.createBooking(booking);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'oggetto booking");
        }
        planning = booking.getPlanning();
        planning.setAvailable(false);
        try {
            planningDao.updatePlanning(planning);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel modificare l'oggetto planning");
        }
        return idBoking;
    }

    @Override
    public void updateBooking(Booking booking) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        PlanningDao planningDao = new PlanningDao();
        Planning planning;
        try {
            bookingDao.updateBooking(booking);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel modificare l'oggetto booking");
        }

        if (booking.getLessonState().equals(2) || booking.getLessonState().equals(3)) {
            planning = booking.getPlanning();
            planning.setAvailable(true);
            try {
                planningDao.updatePlanning(planning);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new BookingBusinessException("Errore nel modificare l'oggetto planning");
            }
        }
    }

    @Override
    public List<Booking> findHistoricalBookingByStudent(Student student) throws BookingBusinessException {

        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getHistoricalBokingsOfAStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }

        return bookings;
    }

    @Override
    public List<Booking> findHistoricalBookingByTeacher(Teacher teacher) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getHistoricalBokingsOfATeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }

        return bookings;
    }

    @Override
    public Booking findStudentByIdPlanning(Integer idPlanning) throws BookingBusinessException {
        BookingDao bookingDao = new BookingDao();
        Booking booking;
        try {
            booking = bookingDao.findStudentByIdPlanning(idPlanning);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'oggetto booking");

        }
        return booking;
    }

    @Override
    public List<Booking> findAllBookingByStudnetAndFilter(Student student, String macroMateria, String nomeLezione, String microMateria, String date, String idUser, String stato) throws ParseException {

        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings = new ArrayList<>();

        int nomeLezioneRevelant = 0;
        if (nomeLezione != null && !nomeLezione.isEmpty()) {
            nomeLezioneRevelant = 1;
        }
        int macroMateriaRelevant = 0;
        if (macroMateria != null && !macroMateria.isEmpty()) {
            macroMateriaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.isEmpty()) {
            microMateriaRelevant = 1;
        }
        int idProfessoreRelevant = 0;
        int idProfessoreAppo = 0;
        if (idUser != null && !idUser.isEmpty()) {
            idProfessoreAppo = Integer.parseInt(idUser);
            idProfessoreRelevant = 1;
        }
        int dateRelevant = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSql = null;
        if (date != null && !date.isEmpty()) {
            java.util.Date dateUtil;
            try {
                dateUtil = sdf.parse(date);
                dateSql = new Date(dateUtil.getTime());
                dateRelevant = 1;
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ParseException("Errore nel parse della data", 0);
            }
        }

        int statoRelevant = 0;
        int statoAppo = 0;
        if (stato != null && !stato.isEmpty()) {
            statoAppo = Integer.parseInt(stato);
            statoRelevant = 1;
        }
        try {
            bookings = bookingDao.getHistoricalBokingsOfAStudentAndFilter(student, macroMateriaRelevant, macroMateria, nomeLezioneRevelant, nomeLezione, microMateriaRelevant, microMateria, dateRelevant, dateSql, idProfessoreRelevant, idProfessoreAppo, statoRelevant, statoAppo);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    @Override
    public List<Booking> findAllBookingByTeacherAndFilter(Teacher teacher, String macroMateria, String nomeLezione, String microMateria, String date, String idUser, String stato) throws ParseException {

        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings = new ArrayList<>();

        int nomeLezioneRevelant = 0;
        if (nomeLezione != null && !nomeLezione.isEmpty()) {
            nomeLezioneRevelant = 1;
        }
        int macroMateriaRelevant = 0;
        if (macroMateria != null && !macroMateria.isEmpty()) {
            macroMateriaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.isEmpty()) {
            microMateriaRelevant = 1;
        }
        int idUserRelevant = 0;
        int idUserAppo = 0;
        if (idUser != null && !idUser.isEmpty()) {
            idUserAppo = Integer.parseInt(idUser);
            idUserRelevant = 1;
        }
        int dateRelevant = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSql = null;
        if (date != null && !date.isEmpty()) {
            java.util.Date dateUtil;
            try {
                dateUtil = sdf.parse(date);
                dateSql = new Date(dateUtil.getTime());
                dateRelevant = 1;
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ParseException("Errore nel parse della data", 0);
            }
        }

        int statoRelevant = 0;
        int statoAppo = 0;
        if (stato != null && !stato.isEmpty()) {
            statoAppo = Integer.parseInt(stato);
            statoRelevant = 1;
        }
        try {
            bookings = bookingDao.getHistoricalBokingsOfATeacherAndFilter(teacher, macroMateriaRelevant, macroMateria, nomeLezioneRevelant, nomeLezione, microMateriaRelevant, microMateria, dateRelevant, dateSql, idUserRelevant, idUserAppo, statoRelevant, statoAppo);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return bookings;
    }


//    private Booking addUsersInBookingList(Booking booking) throws BookingBusinessException, UserException {
//        Booking booking1 = new Booking();
//        booking1 = booking;
//        Planning planning = new Planning();
//        Lesson lesson = new Lesson();
//        Teacher teacher = new Teacher();
//        Student student = new Student();
//
//        StudentDao studentDao = new StudentDao();
//        try {
//            student = studentDao.getStudentByIdUser(booking.getStudent().getIdUser());
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
//        booking1.setStudent(student);
//
//        TeacherDao teacherDao = new TeacherDao();
//        try {
//            teacher = teacherDao.getTeacherByUserID(booking.getPlanning().getLesson().getTeacher().getIdUser());
//        } catch (DatabaseException e) {
//            e.printStackTrace();
//        }
//        planning = booking.getPlanning();
//        lesson = planning.getLesson();
//        lesson.setTeacher(teacher);
//        planning.setLesson(lesson);
//        booking1.setPlanning(planning);
//        return booking1;
//    }

    private ArrayList<Booking> dayOfWeek(List<Booking> bookings, String dom, String lun, String mar, String mer, String gio, String ven, String sab) {
        Calendar c = Calendar.getInstance();
        ArrayList<Booking> bookings1 = new ArrayList<>();
        if (dom != null && !dom.equals("") && dom.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (lun != null && !lun.equals("") && lun.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (mar != null && !mar.equals("") && mar.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (mer != null && !mer.equals("") && mer.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (gio != null && !gio.equals("") && gio.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (ven != null && !ven.equals("") && ven.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        if (sab != null && !sab.equals("") && sab.equals("1")) {
            for (Booking booking: bookings) {
                c.setTime(booking.getPlanning().getDate());
                if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    bookings1.add(new Booking(booking.getIdBooking(), booking.getDate(), booking.getLessonState(),
                            booking.getCreateDate(), booking.getUpdateDate(), booking.getStudent(), booking.getPlanning()));
                }
            }
        }
        return bookings1;
    }


    @Override
    public List<Booking> findAllBookingByDate(Date date) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByLessonAndDate(Date date, Lesson lesson) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public List<Booking> findAllBooking() { return null; }

}
