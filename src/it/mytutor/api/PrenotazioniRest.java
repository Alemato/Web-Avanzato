package it.mytutor.api;

import it.mytutor.business.impl.BookingBusiness;
import it.mytutor.business.services.BookingInterface;
import it.mytutor.domain.Booking;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth/lezioni/{LID}/prenotazioni")
public class PrenotazioniRest {

    private BookingInterface bookingService = new BookingBusiness();
    private String lid;

    public PrenotazioniRest(@PathParam("LID") String lid){
        this.lid = lid;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public String getPrenotazioni(@QueryParam("filtro") String filtro){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Prenotazioni con @QueryParam(\"filtro\"):" + filtro + " (a default vale null)</h1>";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaPrenotazione(Booking booking){
        bookingService.crateBooking(booking);
        return Response.ok().build();
    }

    @Path("{PID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response rispostaTheacher(@PathParam("PID") String pid){
        return Response.ok().build();
    }
}
