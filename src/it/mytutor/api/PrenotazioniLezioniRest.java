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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Path("bookings-lessons")
public class PrenotazioniLezioniRest {
    @Context
    private SecurityContext securityContext;

    private BookingInterface bookingService = new BookingBusiness();
    private UserInterface userService = new UserBusiness();


    /**
     * Rest della HomePage di entrambe le tipologie di utente
     *
     * @return lista di Bookings
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
        } catch (UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        } catch (DatabaseException e) {
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
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (UserException e) {
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
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings).build();
    }

    /**
     * Rest della ricerca delle lezioni dello studente
     *
     * @param macroMateria    macro Materia
     * @param nome            nome Materia
     * @param zona            citta
     * @param microMateria    micro Materia
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
                                           @QueryParam("dom") String dom,
                                           @QueryParam("lun") String lun,
                                           @QueryParam("mar") String mar,
                                           @QueryParam("mer") String mer,
                                           @QueryParam("gio") String gio,
                                           @QueryParam("ven") String ven,
                                           @QueryParam("sab") String sab,
                                           @QueryParam("prezzo") String prezzo,
                                           @QueryParam("ora-inizio") String oraInizio,
                                           @QueryParam("ora-fine") String oraFine) {
        System.out.println("permettimi");

        List<Booking> bookings;
        try {
            bookings = bookingService.findBookingByFilter(macroMateria, nome, zona, microMateria, dom, lun, mar, mer, gio, ven, sab, prezzo, oraInizio, oraFine);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException | UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        return Response.ok(bookings).build();
    }

    /**
     * Rest della pagina dello storico di entrambe le tipologie di utente
     * @return lista di Bookings
     */
    @Path("history")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getStorico() {
        List<Booking> bookings = new ArrayList<>();
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        } catch (DatabaseException e) {
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
                bookings = bookingService.findHistoricalBookingByStudent(student);
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
            catch (DatabaseException e) {
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
                bookings = bookingService.findHistoricalBookingByTeacher(teacher);
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (BookingBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings).build();
    }

    /**
     * Rest della pagina dello storico con il filtro di entrambe le tipologie di utente
     *
     * @param macroMateria macro materia
     * @param nomeLezione  nome della lezione
     * @param microMateria micro materia
     * @param data         data di planning
     * @param idUser       id dello studente prenotato alla lezione
     * @param stato        stato deolla prenotazione (0,1,2,3,4)
     * @return lista di Bookings
     */
    @Path("history/filter")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getStoricoTeachFilter(@QueryParam("macro-materia") String macroMateria,
                                          @QueryParam("nome-lezione") String nomeLezione,
                                          @QueryParam("micro-materia") String microMateria,
                                          @QueryParam("data") String data,
                                          @QueryParam("id-utente") String idUser,
                                          @QueryParam("stato") String stato) {
        List<Booking> bookings = new ArrayList<>();
        User user;
        String email = securityContext.getUserPrincipal().getName();
        try {
            user = (User) userService.findUserByUsername(email);
        } catch (UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        } catch (DatabaseException e) {
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
            } catch (UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (ParseException e) {
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
            } catch (UserException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(bookings).build();
    }
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
        } catch (UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        } catch (DatabaseException e) {
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
}

