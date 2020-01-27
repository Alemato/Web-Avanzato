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
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("lessons/bookings")
public class PrenotazioniRest {
    @Context
    private SecurityContext securityContext;

    private BookingInterface bookingService = new BookingBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Creazione della prenotazione da parte dello studente
     *
     * @param bookings lista di oggetti prenotazione ricevuti dal client
     * @return Response Status ACCEPTED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response creaPrenotazione(List<Booking> bookings){

        String teacherEmail = securityContext.getUserPrincipal().getName();
        Student student;
        try {
            student = (Student) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        for (Booking booking1: bookings){
            booking1.setStudent(student);
        }
        try {
            bookingService.crateBookings(bookings);
        } catch (PlanningBusinessException | BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Rest per l'aggiornamento dello stato della lezione
     * concesso ad entrambe le tipologie di utenti
     *
     *
     *
     * @return Response Status Created
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER", "STUDENT"})
    public Response rispostaTheacher(Booking booking){
        try {
            bookingService.updateBooking(booking);
        } catch (BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(201).entity("{ \"message\": \"prenotazione aggiornata\" }").build();
    }
}
