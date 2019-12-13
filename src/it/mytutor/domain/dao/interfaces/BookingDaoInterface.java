package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.sql.Date;
import java.util.List;

public interface BookingDaoInterface {

    void createBooking(Booking booking)throws DatabaseException;

    void updateBooking(Booking booking)throws DatabaseException;

    List<Booking> getBookingByFilter(int macroMateriaRelevant, String macroMateria, int nomeRelevant,
                                     String nome, int zonaRelevant, String zona, int microMateriaRelevant,
                                     String microMateria, int giornoSettimanaRelevant, String giornoSettimana,
                                     int prezzoRelevant, String prezzo, int oraInizioRelevant,
                                     String oraInizio, int oraFineaRelevant, String oraFine)throws DatabaseException;

    Booking getBookingById(int id)throws DatabaseException;

    List<Booking> getAllBookingOfAStudent(Student student)throws DatabaseException;

    List<Booking> getAllBookingOfATeacher(Teacher teacher) throws DatabaseException;

    List<Booking> getHistoricalBokingsOfAStudent(Student student, int nomeLezioneRevelant, String nomeLezione, int macroMateriaRevelant, String macroMateria, int microMateriaRevelant, String microMateria, int idTeacherRevelant, int idTeacher, int dateRevelant, Date date, int rifiutataRevelant, String rifiutata, int annullataRevelant, String annullata, int eseguitaRevelant, String eseguita) throws DatabaseException;

    List<Booking> getHistoricalBokingsOfATeacher(Teacher teacher, int nomeLezioneRevelant, String nomeLezione, int macroMateriaRevelant, String macroMateria, int microMateriaRevelant, String microMateria, int idTeacherRevelant, int idStudent, int dateRevelant, Date date, int rifiutataRevelant, String rifiutata, int annullataRevelant, String annullata, int eseguitaRevelant, String eseguita) throws DatabaseException;
}
