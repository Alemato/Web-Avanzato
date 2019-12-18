package it.mytutor.api;

import it.mytutor.business.impl.PlanningBusiness;
import it.mytutor.business.services.PlanningInterface;
import it.mytutor.domain.Planning;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("lezioni/pianificazioni")
public class PlanningRest {

    private PlanningInterface planningService = new PlanningBusiness();

    @Path("crea")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({"TEACHER"})
    public Response CreaPlanning(List<Planning> plannings) {
        planningService.CreaPlanning(plannings);
        return Response.ok().build();
    }

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
        List<Planning> plannings;

        plannings = planningService.FindPlanningByFilter(macroMateria, nome, zona, microMateria, giornoSettimana, prezzo, oraInizio, oraFine);

        return  Response.ok(plannings).build();
    }

}
