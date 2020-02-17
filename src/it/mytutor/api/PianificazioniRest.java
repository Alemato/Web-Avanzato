package it.mytutor.api;

import it.mytutor.business.exceptions.*;
import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.impl.PlanningBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.business.services.PlanningInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.*;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


@Path("lessons/plannings")
public class PianificazioniRest {

    @Context
    private SecurityContext securityContext;

    private PlanningInterface planningService = new PlanningBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest di creazione del planning concesa solamente al professore
     *
     * @param planning Lista di Pianificazioni della prenotazione della lezione
     * @return Response Status ACCEPTED
     */
    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response creaPlanning(Planning planning) {
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            planningService.creaPlanning(planning, teacher);
        } catch (PlanningBusinessException | SubjectBusinessException | LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     *  Rest per la modifica dele pianificazioni di una lezione concesa solamente al professore
     *
     * @param plannings lista di oggetti pianificazione
     * @return Risposta custom con stato 201 Created
     */
    @Path("modify")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response updateLesson(List<Planning> plannings, @QueryParam("id-lesson") Integer idLesson) {
        if (plannings.size() > 0) {
            try {
                planningService.updatePlanning(plannings);
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("delete")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response deletePlannings(List<Planning> plannings, @QueryParam("id-lesson") Integer idLesson) {
        if (plannings.size() > 0) {
            try {
                planningService.deletePlannings(plannings);
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else {
            throw new ApiWebApplicationException("lista planning non valida");
        }
        return Response.status(Response.Status.CREATED).build();
    }


    /**
     * Rest  per la ricerca lezioni con possibilità di filtro concesa solamente allo studente
     *
     * @param macroMateria query string filtro marcro materia
     * @param nome query string filtro nome
     * @param zona query string filtro città
     * @param microMateria query string filtro  micro materia
     * @param dom query string filtro giorno settimana domenica
     * @param lun query string filtro giorno settimana lunedì
     * @param mar query string filtro giorno settimana marteì
     * @param mer query string filtro giorno settimana mercoledì
     * @param gio query string filtro giorno settimana giovedì
     * @param ven query string filtro giorno settimana venerdì
     * @param sab query string filtro giorno settimana sabato
     * @param prezzo query string filtro prezzo
     * @param oraInizio query string filtro ora inizio
     * @param oraFine query string filtro ora fine
     * @return lista di oggetti planning
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
        List<Planning> plannings;
        try {
            plannings = planningService.findPlanningByFilter(macroMateria, nome, zona, microMateria, dom, lun, mar, mer, gio, ven, sab, prezzo, oraInizio, oraFine);
        } catch (DatabaseException | PlanningBusinessException | BookingBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(plannings).build();
    }

    /**
     * Rest usata per tornare tutte le pianificazioni collegate ad una lezione identificata dall'id LID
     *
     * @param idLesson id della lezione in oggetto
     * @return lista di planning
     */
    @Path("{LID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getPlanningsForALesson(@PathParam("LID") Integer idLesson) {
        List<Planning> plannings;
            try {
                plannings = planningService.findAllPlanningByLessonId(idLesson);
            } catch (PlanningBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        return Response.ok(plannings).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getPlanningsOfATeacherAsLesson() {
        List<Planning> plannings;
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }

        try {
            plannings = planningService.findPlanningsOfATeacherAsLesson(teacher);
        } catch (PlanningBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(plannings).build();
    }
}
