package it.mytutor.api;


import it.mytutor.business.impl.BookingBusiness;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.Booking;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("auth/prenotazioni-lezioni")
public class PrenotazioniLezioniRest {

    private BookingInterface bookingService = new BookingBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public Response getMessaggi(@QueryParam("filtro") String filtro){

        //TODO Prendere user da SID
        User user = new User();
        //TODO get student by User
        List<Booking> bookings = new ArrayList<>();

//        if (user == studente)
            //TODO get student by User
            Student student = new Student();
            bookings = bookingService.findAllBookingByStudnet(student);
//        else
            //TODO get teacher by user
            Teacher teacher = new Teacher();
            bookings = bookingService.findAllBookingByTeacher(teacher);

        List<Booking> bookings20 = new ArrayList<>();
        Integer index = 0;
        Integer sotto = 0;

        if (filtro != null){

            //riconoscimento stato in filtro filtro, controllo se presente
            List<Booking> bookingsFiltroStato = new ArrayList<>();
            Integer stato = 0;
            for (Booking booking: bookings){
                if (booking.getLessonState().equals(stato)) {
                    bookingsFiltroStato.add(booking);
                }
            }
            //riconoscimento offset e sotto da filtro se presenti
            //

        }

        return Response.ok().build();
    }
}

