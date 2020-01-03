package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.*;
import it.mytutor.business.impl.PlanningBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.PlanningInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


@Path("lezioni/pianificazioni")
public class PlanningRest {

    @Context
    private SecurityContext securityContext;

    private PlanningInterface planningService = new PlanningBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest Creazione del planning concesa solamente al professore
     *
     * @param plannings Lista di Pianificazioni della prenotazione della lezione
     * @return Response Status ACCEPTED
     */
    @Path("crea")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"TEACHER"})
    public Response creaPlanning(List<Planning> plannings) {

        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            planningService.creaPlanning(plannings, teacher);
        } catch (PlanningBusinessException | SubjectBusinessException | LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(Response.Status.ACCEPTED).entity("Lezione pianificata").build();
    }

    @Path("modifica")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"TEACHER"})
    public Response updateLesson(List<Planning> plannings) {
        try {
            planningService.updatePlanning(plannings);
        } catch (PlanningBusinessException e) {
            e.printStackTrace();
        }
        return Response.ok(Response.Status.ACCEPTED).entity("Lezione modificata").build();
    }

    //TODO aggere AddPlanning
    //

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
                                           @QueryParam("ora-fine") String oraFine) {
        List<Planning> plannings;
        try {
            plannings = planningService.FindPlanningByFilter(macroMateria, nome, zona, microMateria, giornoSettimana, prezzo, oraInizio, oraFine);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return  Response.ok(plannings).build();
    }

    //TODO rest per liste dei campi in storico lezione per professore e per studente per Booking LessonState 2,3,4

}
