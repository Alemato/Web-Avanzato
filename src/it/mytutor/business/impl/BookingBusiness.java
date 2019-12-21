package it.mytutor.business.impl;

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

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllBooking;

public class BookingBusiness implements BookingInterface {

    @Override
    public List<Booking> findAllBooking() {

        return null;
    }

    public List<Booking> findBookingByFilter(String macroMateria, String nome, String zona, String microMateria,
                                             String giornoSettimana, String prezzo, String oraInizio, String oraFine) {
        UserDao userDao = new UserDao();
        User user = new User();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Teacher teacher = new Teacher();
        Student student = new Student();
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings = new ArrayList<>();
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
        }

        for (Booking booking : bookings) {
            addUsersInBookingList(booking, user, userDao, planning, lesson, teacher, student);
        }
        return bookings;
    }

    @Override
    public Booking findBookingById(Integer idBooking) {
        return null;
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
    public List<Booking> findAllBookingByStudnet(Student student) {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings = new ArrayList<>();
        User user = new User();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Teacher teacher = new Teacher();
        Student student1 = new Student();


        try {
            bookings = bookingDao.getAllBookingOfAStudent(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        for (Booking booking : bookings) {
            addUsersInBookingList(booking, user, userDao, planning, lesson, teacher, student1);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByTeacher(Teacher teacher) {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings = new ArrayList<>();
        User user = new User();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Teacher teacher1 = new Teacher();
        Student student1 = new Student();


        try {
            bookings = bookingDao.getAllBookingOfATeacher(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        for (Booking booking : bookings) {
            addUsersInBookingList(booking, user, userDao, planning, lesson, teacher1, student1);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAllBookingByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public void crateBooking(Booking booking) {
        BookingDaoInterface bookingDao = new BookingDao();
        try {
            bookingDao.createBooking(booking);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking updateBooking(Booking booking, Integer lessonState) {
        return null;
    }

    @Override
    public List<Booking> findHistoricalBookingByStudent(Student student, String nomeLezione, String macroMateria, String microMateria,
                                                        String idTeacher, String date, String rifiutata, String annullata, String eseguita) {

        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings = new ArrayList<>();
        User user = new User();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Teacher teacher = new Teacher();
        Student student1 = new Student();

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
        int idTeacherRelevant = 0;
        int idTeacherAppo = 0;
        if (idTeacher != null && !idTeacher.isEmpty()) {
            idTeacherAppo = Integer.parseInt(idTeacher);
            idTeacherRelevant = 1;
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
            }
        }
        int rifiutataRelevant = 0;
        if (rifiutata != null && !rifiutata.isEmpty()) {
            rifiutataRelevant = 1;
        }
        int annullataRelevant = 0;
        if (annullata != null && !annullata.isEmpty()) {
            annullataRelevant = 1;
        }
        int eseguitaRelevant = 0;
        if (eseguita != null && !eseguita.isEmpty()) {
            eseguitaRelevant = 1;
        }

        try {
            bookings = bookingDao.getHistoricalBokingsOfAStudent(student, nomeLezioneRevelant, nomeLezione, macroMateriaRelevant,
                    macroMateria, microMateriaRelevant, microMateria, idTeacherRelevant, idTeacherAppo, dateRelevant, dateSql,
                    rifiutataRelevant, rifiutata, annullataRelevant, annullata, eseguitaRelevant, eseguita);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        for (Booking booking : bookings) {
            addUsersInBookingList(booking, user, userDao, planning, lesson, teacher, student1);
        }

        return bookings;
    }

    @Override
    public List<Booking> findHistoricalBookingByTeacher(Teacher teacher, String nomeLezione, String macroMateria, String microMateria,
                                                        String idStudent, String date, String rifiutata, String annullata, String eseguita) {
        BookingDao bookingDao = new BookingDao();
        UserDao userDao = new UserDao();
        List<Booking> bookings = new ArrayList<>();
        User user = new User();
        Planning planning = new Planning();
        Lesson lesson = new Lesson();
        Teacher teacher1 = new Teacher();
        Student student = new Student();

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
        int idStudentRelevant = 0;
        int idStudentAppo = 0;
        if (idStudent != null && !idStudent.isEmpty()) {
            idStudentAppo = Integer.parseInt(idStudent);
            idStudentRelevant = 1;
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
            }
        }
        int rifiutataRelevant = 0;
        if (rifiutata != null && !rifiutata.isEmpty()) {
            rifiutataRelevant = 1;
        }
        int annullataRelevant = 0;
        if (annullata != null && !annullata.isEmpty()) {
            annullataRelevant = 1;
        }
        int eseguitaRelevant = 0;
        if (eseguita != null && !eseguita.isEmpty()) {
            eseguitaRelevant = 1;
        }

        try {
            bookings = bookingDao.getHistoricalBokingsOfATeacher(teacher, nomeLezioneRevelant, nomeLezione, macroMateriaRelevant,
                    macroMateria, microMateriaRelevant, microMateria, idStudentRelevant, idStudentAppo, dateRelevant, dateSql,
                    rifiutataRelevant, rifiutata, annullataRelevant, annullata, eseguitaRelevant, eseguita);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        for (Booking booking : bookings) {
            addUsersInBookingList(booking, user, userDao, planning, lesson, teacher1, student);
        }

        return bookings;
    }


    private void addUsersInBookingList(Booking booking, User user, UserDao userDao, Planning planning, Lesson lesson, Teacher teacher, Student student) {
        planning = booking.getPlanning();
        lesson = planning.getLesson();
        teacher = lesson.getTeacher();
        try {
            user = userDao.getUserById(teacher.getIdUser());
        } catch (DatabaseException e) {
            e.printStackTrace();
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

        student = booking.getStudent();
        try {
            user = userDao.getUserById(student.getIdStudent());
        } catch (DatabaseException e) {
            e.printStackTrace();
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
    }

}
