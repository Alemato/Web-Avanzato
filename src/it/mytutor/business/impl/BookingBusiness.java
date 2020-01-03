package it.mytutor.business.impl;

import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.*;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.BookingDao;
import it.mytutor.domain.dao.implement.UserDao;
import it.mytutor.domain.dao.interfaces.BookingDaoInterface;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookingBusiness implements BookingInterface {

    @Override
    public List<Booking> findAllBooking() {

        return null;
    }

    public List<Booking> findBookingByFilter(String macroMateria, String nome, String zona, String microMateria,
                                             String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws PlanningBusinessException, UserException {
        UserDao userDao = new UserDao();
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;
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
            bookings = bookingDao.getBookingByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome,
                    zonaRelevant, zona, microMateriaRelevant, microMateria, giornoSettimanaRelevant, giornoSettimana,
                    prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere gli oggetti booking");
        }

        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
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
    public List<Booking> findAllBookingByDate(Date date) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByLessonAndDate(Date date, Lesson lesson) {
        return null;
    }

    @Override
    public List<Booking> findAllBookingByStudnet(Student student) throws BookingBusinessException, UserException {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings;


        try {
            bookings = bookingDao.getAllBookingOfAStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }
        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByTeacher(Teacher teacher) throws BookingBusinessException, UserException {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getAllBookingOfATeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }
        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public void crateBooking(Booking booking) throws PlanningBusinessException {
        BookingDaoInterface bookingDao = new BookingDao();
        try {
            bookingDao.createBooking(booking);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new PlanningBusinessException("Errore nel prendere l'oggetto booking");
        }
    }

    @Override
    public void updateBooking(Booking booking, Integer lessonState) throws BookingBusinessException {
       BookingDaoInterface bookingDao = new BookingDao();
       booking.setLessonState(lessonState);
        try {
            bookingDao.updateBooking(booking);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel modificare l'oggetto booking");
        }
    }

    @Override
    public List<Booking> findHistoricalBookingByStudent(Student student) throws BookingBusinessException, UserException {

        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getHistoricalBokingsOfAStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }

        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
        }

        return bookings;
    }

    @Override
    public List<Booking> findHistoricalBookingByTeacher(Teacher teacher) throws BookingBusinessException, UserException {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings;

        try {
            bookings = bookingDao.getHistoricalBokingsOfATeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere gli oggetti booking");
        }

        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");

            }
        }

        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByStudnetAndFilter(Student student, String macroMateria, String nomeLezione, String microMateria, String date, String idProfessore, String stato) throws ParseException, UserException {

        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
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
        if (idProfessore != null && !idProfessore.isEmpty()) {
            idProfessoreAppo = Integer.parseInt(idProfessore);
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
        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (BookingBusinessException e) {
                e.printStackTrace();
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
        }
        return bookings;
    }


    @Override
    public List<Booking> findAllBookingByTeacherAndFilter(Teacher teacher, String macroMateria, String nomeLezione, String microMateria, String date, String idStudente, String stato) throws ParseException, UserException {

        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
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
        int idStudenteRelevant = 0;
        int idStudenteAppo = 0;
        if (idStudente != null && !idStudente.isEmpty()) {
            idStudenteAppo = Integer.parseInt(idStudente);
            idStudenteRelevant = 1;
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
            bookings = bookingDao.getHistoricalBokingsOfATeacherAndFilter(teacher, macroMateriaRelevant, macroMateria, nomeLezioneRevelant, nomeLezione, microMateriaRelevant, microMateria, dateRelevant, dateSql, idStudenteRelevant, idStudenteAppo, statoRelevant, statoAppo);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        int i = 0;
        for (Booking booking : bookings) {
            try {
                bookings.set(i, addUsersInBookingList(booking, userDao));
                i++;
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new UserException("Errore nel prendere booking dal database");
            } catch (UserException e) {
                e.printStackTrace();
                throw new UserException("Errore nel'aggiunta degli user");
            }
        }
        return bookings;
    }


    private Booking addUsersInBookingList(Booking booking, UserDao userDao) throws BookingBusinessException, UserException {
        Planning planning = booking.getPlanning();
        Lesson lesson = planning.getLesson();
        Teacher teacher = lesson.getTeacher();
        User user;
        try {
            user = userDao.getUserById(teacher.getIdUser());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new UserException("Errore nel prendere l'user");
        }

        teacher.setIdUser(user.getIdUser());
        teacher.setEmail(user.getEmail());
        teacher.setRoles(user.getRoles());
        teacher.setPassword(user.getPassword());
        teacher.setName(user.getName());
        teacher.setSurname(user.getSurname());
        teacher.setBirthday(user.getBirthday());
        teacher.setLanguage(user.getLanguage());
        teacher.setImage(user.getImage());
        teacher.setCreateDate(user.getCreateDate());
        teacher.setUpdateDate(user.getUpdateDate());

        lesson.setTeacher(teacher);
        planning.setLesson(lesson);
        booking.setPlanning(planning);

        Student student = booking.getStudent();
        try {
            user = userDao.getUserById(student.getIdStudent());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new BookingBusinessException("Errore nel prendere l'user");
        }

        student.setIdUser(user.getIdUser());
        student.setEmail(user.getEmail());
        student.setRoles(user.getRoles());
        student.setPassword(user.getPassword());
        student.setName(user.getName());
        student.setSurname(user.getSurname());
        student.setBirthday(user.getBirthday());
        student.setLanguage(user.getLanguage());
        student.setImage(user.getImage());
        student.setCreateDate(user.getCreateDate());
        student.setUpdateDate(user.getUpdateDate());

        booking.setStudent(student);

        return booking;
    }

}
