package it.mytutor.api;

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
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.StudentDao;
import it.mytutor.domain.dao.implement.TeacherDao;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Path("lessons/bookings")
public class PrenotazioniRest {
    @Context
    private SecurityContext securityContext;
    private BookingInterface bookingService = new BookingBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest usata per tornare un singolo booking
     *
     * @param idBoking id del booking singolo
     * @return lista di Bookings
     */
    @Path("{ID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getBooking(@PathParam("ID") String idBoking) {
        Booking booking;
        if (idBoking != null && !idBoking.isEmpty() && !idBoking.equals(" ")) {
            try {
                booking = bookingService.findBookingById(Integer.parseInt(idBoking));
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else throw new ApiWebApplicationException("Bad request:  id non valido", 400);
        return Response.ok(booking).build();
    }


    /**
     * Rest per la creazione di una prenotazione effettuata da uno studente
     *
     * @param booking lista di oggetti prenotazione ricevuti dal client
     * @return Response Status ACCEPTED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response creaPrenotazione(Booking booking){
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Student student;
        int id = -1;
        try {
            student = (Student) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        booking.setStudent(student);
        try {
            id = bookingService.crateBooking(booking);
        } catch (PlanningBusinessException | BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.created(URI.create("http://localhost:8080/api/lessons/bookings/" + id)).build();
    }

    /**
     * Rest per l'aggiornamento dello stato della lezione concesso ad entrambe le tipologie di utenti
     *
     * @param booking oggetto prenotazione da modificare con le modifiche da effettuare
     * @return Risposta custom con stato 201 Created
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER", "STUDENT"})
    public Response updateBooking(Booking booking){
        try {
            bookingService.updateBooking(booking);
        } catch (BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Rest che si occhupa di tornare la lista delle prenotazioni usate per popolare la home page dell'applicazione
     *
     * @return lista di oggetti Booking
     */
    @Path("home")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER", "STUDENT"})
    public Response getPrenotazioni() {
        List<Booking> bookings = new ArrayList<>();
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        if (user.getRoles() == 1) {
            StudentDao studentDao = new StudentDao();
            Student student;
            try {
                student = studentDao.getStudentByIdUser(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllBookingByStudnet(student);
            } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else if (user.getRoles() == 2) {
            TeacherDao teacherDao = new TeacherDao();
            Teacher teacher;
            try {
                teacher = teacherDao.getTeacherByUserID(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllBookingByTeacher(teacher);
            } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings).build();
    }

    /**
     * Rest che si occupa di tornare la lista dello storico delle prenotazioni usata nella pagina storico dell'app
     *
     * @param macroMateria macro materia
     * @param nomeLezione  nome della lezione
     * @param data         data di planning
     * @param idUser       id dello studente prenotato alla lezione
     * @param stato        stato deolla prenotazione (0,1,2,3,4)
     * @return lista di Bookings
     */
    @Path("history")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getStoricoTeachFilter(@QueryParam("macro-materia") String macroMateria,
                                          @QueryParam("micro-materia") String microMateria,
                                          @QueryParam("nome-lezione") String nomeLezione,
                                          @QueryParam("data") String data,
                                          @QueryParam("id-utente") String idUser,
                                          @QueryParam("stato") String stato) {
        List<Booking> bookings = new ArrayList<>();
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        if (user.getRoles() == 1) {
            StudentDao studentDao = new StudentDao();
            Student student;
            try {
                student = studentDao.getStudentByIdUser(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllBookingByStudnetAndFilter(student, macroMateria, nomeLezione, microMateria, data, idUser, stato);
            } catch (UserException | ParseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else if (user.getRoles() == 2) {
            TeacherDao teacherDao = new TeacherDao();
            Teacher teacher;
            try {
                teacher = teacherDao.getTeacherByUserID(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllBookingByTeacherAndFilter(teacher, macroMateria, nomeLezione, microMateria, data, idUser, stato);
            } catch (UserException | ParseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings).build();
    }

    /**
     * Rest che ritorna il numero delle prenotzioni collegate all'utente che la richiede
     *
     * @return int
     */
    @Path("count")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response countbooking() {
        List<Booking> bookings = new ArrayList<>();
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        if (user.getRoles() == 1) {
            StudentDao studentDao = new StudentDao();
            Student student;
            try {
                student = studentDao.getStudentByIdUser(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllbookedUpByStudent(student);
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else if (user.getRoles() == 2) {
            TeacherDao teacherDao = new TeacherDao();
            Teacher teacher;
            try {
                teacher = teacherDao.getTeacherByUserID(user.getIdUser());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            try {
                bookings = bookingService.findAllbookedUpByTeacher(teacher);
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings.size()).build();
    }

    @Path("planning/{PID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getBookingByIdPlanning(@PathParam("PID") String idPlanning) {
        Booking booking;
        if (idPlanning != null && !idPlanning.isEmpty() && !idPlanning.equals(" ")) {
            try {
                 booking = bookingService.findStudentByIdPlanning(Integer.parseInt(idPlanning));
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else throw new ApiWebApplicationException("Bad request:  id non valido", 400);
        if (booking.getIdBooking() != null){
            return Response.ok(booking).build();
        } else {
            return Response.ok().build();
        }
    }
}
