package it.mytutor.api;


import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.BookingBusinessException;
import it.mytutor.business.exceptions.PlanningBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.BookingBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.ParseException;
import java.util.List;

@Path("prenotazioni-lezioni")
public class PrenotazioniLezioniRest {

    private BookingInterface bookingService = new BookingBusiness();
    private UserInterface userService = new UserBusiness();


    /**
     * Rest della HomePage del Professore
     *
     * @param context ContainerRequestContext da cui prendere l'email dell'utente
     * @return lista di Bookings
     */
    @Path("home-teach")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getPrenotazioniTeach(ContainerRequestContext context) {
        List<Booking> bookings;
        SecurityContext securityContext = context.getSecurityContext();
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            bookings = bookingService.findAllBookingByTeacher(teacher);
        } catch (PlanningBusinessException | BookingBusinessException | UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(bookings).build();
    }

    /**
     * Rest della HomePage dello Studente
     *
     * @param context ContainerRequestContext da cui riprendere l'email dell'utente
     * @return lista di Bookings
     */
    @Path("home-stud")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getPrenotazioniStud(ContainerRequestContext context) {
        List<Booking> bookings;
        System.out.println("lo studente è:");
        SecurityContext securityContext = context.getSecurityContext();
        String studentEmail = securityContext.getUserPrincipal().getName();
        System.out.println(studentEmail);
        Student student;
        try {
            student = (Student) userService.findUserByUsername(studentEmail);
            System.out.println(student);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            bookings = bookingService.findAllBookingByStudnet(student);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        System.out.println(bookings);

        return Response.ok(bookings).build();
    }

    /**
     * Rest della ricerca delle lezioni dello studente
     *
     * @param macroMateria    macro Materia
     * @param nome            nome Materia
     * @param zona            citta
     * @param microMateria    micro Materia
     * @param giornoSettimana giorno della Settimana
     * @param prezzo          costo lezione
     * @param oraInizio       ora Inizio slot
     * @param oraFine         ora Fine slot
     * @return Lista di Boking
     */
    @Path("research")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getPrenotazioniRicerca(@QueryParam("macro-materia") String macroMateria,
                                           @QueryParam("nome-lezione") String nome,
                                           @QueryParam("zona") String zona,
                                           @QueryParam("micro-materia") String microMateria,
                                           @QueryParam("giorno-settimana") String giornoSettimana,
                                           @QueryParam("prezzo") String prezzo,
                                           @QueryParam("ora-inizio") String oraInizio,
                                           @QueryParam("ora-fine") String oraFine) {

        List<Booking> bookings;
        try {
            bookings = bookingService.findBookingByFilter(macroMateria, nome, zona, microMateria, giornoSettimana, prezzo, oraInizio, oraFine);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        return Response.ok(bookings).build();
    }

    /**
     * @param context ContainerRequestContext da cui riprendere l'email dell'utente
     * @return lista di Bookings
     */
    @Path("storico-stud")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getStoricoStud(ContainerRequestContext context) {
        List<Booking> bookings;
        SecurityContext securityContext = context.getSecurityContext();
        String studentEmail = securityContext.getUserPrincipal().getName();
        Student student;
        try {
            student = (Student) userService.findUserByUsername(studentEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            bookings = bookingService.findAllBookingByStudnet(student);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        return Response.ok(bookings).build();
    }

    /**
     * @param context ContainerRequestContext  da cui riprendere l'email dell'utente
     * @return lista di Bookings
     */
    @Path("storico/teach")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getStoricoTeach(ContainerRequestContext context) {
        List<Booking> bookings;
        SecurityContext securityContext = context.getSecurityContext();
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            bookings = bookingService.findAllBookingByTeacher(teacher);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(bookings).build();
    }


    /**
     * @param context      da cui riprendere l'email dell'utente
     * @param macroMateria macro materia
     * @param nomeLezione  nome della lezione
     * @param microMateria micro materia
     * @param data         data di planning
     * @param idProfessore id del professore che tiene la lezione
     * @param stato        stato deolla prenotazione (0,1,2,3,4)
     * @return lista di Bookings
     */
    @Path("storico/stud-filter")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getStoricoStudFilter(ContainerRequestContext context, @QueryParam("macro-materia") String macroMateria,
                                         @QueryParam("nome-lezione") String nomeLezione,
                                         @QueryParam("micro-materia") String microMateria,
                                         @QueryParam("data") String data,
                                         @QueryParam("id-professore") String idProfessore,
                                         @QueryParam("stato") String stato) {
        List<Booking> bookings;
        SecurityContext securityContext = context.getSecurityContext();
        String studentEmail = securityContext.getUserPrincipal().getName();
        Student student = null;
        try {
            student = (Student) userService.findUserByUsername(studentEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
        }
        try {
            bookings = bookingService.findAllBookingByStudnetAndFilter(student, macroMateria, nomeLezione, microMateria, data, idProfessore, stato);
        } catch (ParseException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        return Response.ok(bookings).build();
    }

    /**
     * @param context      da cui riprendere l'email dell'utente
     * @param macroMateria macro materia
     * @param nomeLezione  nome della lezione
     * @param microMateria micro materia
     * @param data         data di planning
     * @param idStudente   id dello studente prenotato alla lezione
     * @param stato        stato deolla prenotazione (0,1,2,3,4)
     * @return lista di Bookings
     */
    @Path("storico/teach-filter")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getStoricoTeachFilter(ContainerRequestContext context, @QueryParam("macro-materia") String macroMateria,
                                          @QueryParam("nome-lezione") String nomeLezione,
                                          @QueryParam("micro-materia") String microMateria,
                                          @QueryParam("data") String data,
                                          @QueryParam("id-student") String idStudente,
                                          @QueryParam("stato") String stato) {
        List<Booking> bookings;
        SecurityContext securityContext = context.getSecurityContext();
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            bookings = bookingService.findAllBookingByTeacherAndFilter(teacher, macroMateria, nomeLezione, microMateria, data, idStudente, stato);
        } catch (ParseException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        return Response.ok(bookings).build();
    }

}

