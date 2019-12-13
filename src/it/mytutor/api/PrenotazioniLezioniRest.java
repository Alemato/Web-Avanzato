package it.mytutor.api;


import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.BookingBusiness;
import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Path("prenotazioni-lezioni")
public class PrenotazioniLezioniRest {

    private BookingInterface bookingService = new BookingBusiness();
    private LessonInterface lessonService = new LessonBusiness();
    private UserInterface userService = new UserBusiness();


    /**
     *  Rest della HomePage del Professore
     * @param sc SecurityContext da cui riprendere l'email dell'utente
     * @return lista di Bookings
     * @throws UserException  eccezione user
     */
    @Path("teach")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getPrenotazioniTeach(SecurityContext sc) throws UserException, DatabaseException {
        List<Booking> bookings;
        String teacherEmail = sc.getUserPrincipal().getName();
        Teacher teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        bookings = bookingService.findAllBookingByTeacher(teacher);
        return  Response.ok(bookings).build();
    }

    /**
     *  Rest della HomePage dello Studente
     * @param sc SecurityContext da cui riprendere l'email dell'utente
     * @return lista di Bookings
     * @throws UserException eccezione user
     */
    @Path("stud")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getPrenotazioniStud(SecurityContext sc) throws UserException, DatabaseException {
        List<Booking> bookings;
        String studentEmail = sc.getUserPrincipal().getName();
        Student student = (Student) userService.findUserByUsername(studentEmail);
        bookings = bookingService.findAllBookingByStudnet(student);

        return  Response.ok(bookings).build();
    }

    /**
     * Rest della ricerca delle lezioni dello studente
     * @param macroMateria macro Materia
     * @param nome nome Materia
     * @param zona citta
     * @param microMateria micro Materia
     * @param giornoSettimana giorno della Settimana
     * @param prezzo costo lezione
     * @param oraInizio ora Inizio slot
     * @param oraFine ora Fine slot
     * @return Lista di Boking
     * @throws DatabaseException eccezione database
     */
    @Path("research")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getPrenotazioniRicerca(@QueryParam("macro-materia") String macroMateria,
                                           @QueryParam("nome") String nome,
                                           @QueryParam("zona") String zona,
                                           @QueryParam("micro-materia") String microMateria,
                                           @QueryParam("giorno-settimana") String giornoSettimana,
                                           @QueryParam("prezzo") String prezzo,
                                           @QueryParam("ora-inizio") String oraInizio,
                                           @QueryParam("ora-fine") String oraFine) throws DatabaseException {
        List<Booking> bookings;

        bookings = bookingService.findBookingByFilter(macroMateria, nome, zona, microMateria, giornoSettimana, prezzo, oraInizio, oraFine);

        return  Response.ok(bookings).build();
    }

    @Path("storico/stud")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getStoricoStud(SecurityContext sc) throws UserException, DatabaseException {
        List<Booking> bookings;
        String studentEmail = sc.getUserPrincipal().getName();
        Student student = (Student) userService.findUserByUsername(studentEmail);
        bookings = bookingService.findAllBookingByStudnet(student);

        return  Response.ok(bookings).build();
    }

    @Path("storico/teach")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getStoricoTeach(SecurityContext sc) throws UserException, DatabaseException {
        List<Booking> bookings;
        String teacherEmail = sc.getUserPrincipal().getName();
        Teacher teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        bookings = bookingService.findAllBookingByTeacher(teacher);
        return  Response.ok(bookings).build();
    }

}

