package it.mytutor.business.impl;

import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.BookingDao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static it.mytutor.business.impl.test.TestBusinness.simulateFindAllBooking;

public class BookingBusiness implements BookingInterface {

    @Override
    public List<Booking> findAllBooking() {

        return simulateFindAllBooking();
    }

    public List<Booking> findBookingByFilter(String macroMateria, String nome, String zona, String microMateria,
                                           String giornoSettimana, String prezzo, String oraInizio, String oraFine) throws DatabaseException {
        BookingDao bookingDao = new BookingDao();
        List<Booking> bookings;
        int macroMateriaRelevant  = 0;
        if (macroMateria != null && !macroMateria.isEmpty()){
            macroMateriaRelevant = 1;
        }
        int nomeRelevant  = 0;
        if (nome != null && !nome.isEmpty()){
            nomeRelevant = 1;
        }
        int zonaRelevant  = 0;
        if (zona != null && !zona.isEmpty()){
            zonaRelevant = 1;
        }
        int microMateriaRelevant  = 0;
        if (microMateria != null && !microMateria.isEmpty()){
            microMateriaRelevant = 1;
        }
        int giornoSettimanaRelevant  = 0;
        if (giornoSettimana != null && !giornoSettimana.isEmpty()){
            giornoSettimanaRelevant = 1;
        }
        int prezzoRelevant  = 0;
        if (prezzo != null && !prezzo.isEmpty()){
            prezzoRelevant = 1;
        }
        int oraInizioRelevant  = 0;
        if (oraInizio != null && !oraInizio.isEmpty()){
            oraInizioRelevant = 1;
        }
        int oraFineaRelevant  = 0;
        if (oraFine != null && !oraFine.isEmpty()){
            oraFineaRelevant = 1;
        }

        bookings = bookingDao.getBookingByFilter(macroMateriaRelevant, macroMateria, nomeRelevant, nome,
                zonaRelevant, zona, microMateriaRelevant, microMateria, giornoSettimanaRelevant, giornoSettimana,
                prezzoRelevant, prezzo, oraInizioRelevant, oraInizio, oraFineaRelevant, oraFine);
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
    public List<Booking> findAllBookingByStudnet(Student student) throws DatabaseException {
        BookingDao bookingDao = new BookingDao();
        return bookingDao.getAllBookingOfAStudent(student);
    }

    @Override
    public List<Booking> findAllBookingByTeacher(Teacher teacher) throws DatabaseException {
        BookingDao bookingDao = new BookingDao();
        return bookingDao.getAllBookingOfATeacher(teacher);
    }

    @Override
    public List<Booking> findAllBookingByLesson(Lesson lesson) {
        return null;
    }

    @Override
    public Booking crateBooking(Booking booking) {
        return null;
    }

    @Override
    public Booking updateBooking(Booking booking, Integer lessonState) {
        return null;
    }

    @Override
    public List<Booking> findHistoricalBookingByStudent(Student student, String nomeLezione, String macroMateria, String microMateria,
                                                        String idTeacher, String date, String rifiutata, String annullata, String eseguita) throws DatabaseException, ParseException {
        BookingDao bookingDao = new BookingDao();
        int nomeLezioneRevelant = 0;
        if (nomeLezione != null && !nomeLezione.isEmpty()){
            nomeLezioneRevelant = 1;
        }
        int macroMateriaRelevant  = 0;
        if (macroMateria != null && !macroMateria.isEmpty()){
            macroMateriaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.isEmpty()){
            microMateriaRelevant = 1;
        }
        int idTeacherRelevant = 0;
        int idTeacherAppo = 0;
        if (idTeacher != null && !idTeacher.isEmpty()){
            idTeacherAppo = Integer.parseInt(idTeacher);
            idTeacherRelevant = 1;
        }
        int dateRelevant = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSql = null;
        if (date != null && !date.isEmpty()){
            java.util.Date dateUtil = sdf.parse(date);
            dateSql = new Date(dateUtil.getTime());
            dateRelevant = 1;
        }
        int rifiutataRelevant = 0;
        if (rifiutata != null && !rifiutata.isEmpty()){
            rifiutataRelevant = 1;
        }
        int annullataRelevant = 0;
        if (annullata != null && !annullata.isEmpty()){
            annullataRelevant = 1;
        }
        int eseguitaRelevant = 0;
        if (eseguita != null && !eseguita.isEmpty()){
            eseguitaRelevant = 1;
        }

        return bookingDao.getHistoricalBokingsOfAStudent(student, nomeLezioneRevelant, nomeLezione, macroMateriaRelevant,
                macroMateria, microMateriaRelevant, microMateria, idTeacherRelevant, idTeacherAppo, dateRelevant, dateSql,
                rifiutataRelevant, rifiutata, annullataRelevant, annullata, eseguitaRelevant, eseguita);
    }

    @Override
    public List<Booking> findHistoricalBookingByTeacher(Teacher teacher, String nomeLezione, String macroMateria, String microMateria,
                                                        String idStudent, String date, String rifiutata, String annullata, String eseguita) throws DatabaseException{
        BookingDao bookingDao = new BookingDao();
        int nomeLezioneRevelant = 0;
        if (nomeLezione != null && !nomeLezione.isEmpty()){
            nomeLezioneRevelant = 1;
        }
        int macroMateriaRelevant  = 0;
        if (macroMateria != null && !macroMateria.isEmpty()){
            macroMateriaRelevant = 1;
        }
        int microMateriaRelevant = 0;
        if (microMateria != null && !microMateria.isEmpty()){
            microMateriaRelevant = 1;
        }
        int idStudentRelevant = 0;
        int idStudentAppo = 0;
        if (idStudent != null && !idStudent.isEmpty()){
            idStudentAppo = Integer.parseInt(idStudent);
            idStudentRelevant = 1;
        }
        int dateRelevant = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSql = null;
        if (date != null && !date.isEmpty()){
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
        if (rifiutata != null && !rifiutata.isEmpty()){
            rifiutataRelevant = 1;
        }
        int annullataRelevant = 0;
        if (annullata != null && !annullata.isEmpty()){
            annullataRelevant = 1;
        }
        int eseguitaRelevant = 0;
        if (eseguita != null && !eseguita.isEmpty()){
            eseguitaRelevant = 1;
        }

        return bookingDao.getHistoricalBokingsOfATeacher(teacher, nomeLezioneRevelant, nomeLezione, macroMateriaRelevant,
                macroMateria, microMateriaRelevant, microMateria, idStudentRelevant, idStudentAppo, dateRelevant, dateSql,
                rifiutataRelevant, rifiutata, annullataRelevant, annullata, eseguitaRelevant, eseguita);
    }
}
