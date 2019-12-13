package it.mytutor.api;

import io.jsonwebtoken.lang.Strings;
import it.mytutor.business.impl.BookingBusiness;
import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Path("auth/lezioni/{LID}/prenotazioni")
public class PrenotazioniRest {

    private BookingInterface bookingService = new BookingBusiness();
    private String lid;

    public PrenotazioniRest(@PathParam("LID") String lid){
        this.lid = lid;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response creaPrenotazione(Booking booking){
        bookingService.crateBooking(booking);
        return Response.ok().build();
    }

    @Path("{PID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response rispostaTheacher(@PathParam("PID") Integer pid, @QueryParam("status") Integer lessonState){
        bookingService.updateBooking(bookingService.findBookingById(pid), lessonState);
        return Response.ok().build();
    }
}
