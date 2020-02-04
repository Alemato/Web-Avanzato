package it.mytutor.api;

import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.SubjectBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Subject;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("subject")
public class MaterieRest {
    @Context
    private SecurityContext securityContext;


    private SubjectInterface subjectService = new SubjectBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest che ritorna la lista degli oggetti materie.
     * usata per i menu di select inserimento lezione, ricerca lezione e storico dell'app
     *
     * @param storico parametro che identifica il fatto che si fa la richiesta dalla pagina dello storico
     *                e quindi gli oggetti materia da tornare devono essere solo quelli collegati alle lezioni
     *                associate all'utente che richiede la Rest che sono state prenotate
     * @return lista di subject contenenti materie e micromaterie usate per i menu select
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getMaterie(@QueryParam("history") String storico) {
        List<Subject> subjects = new ArrayList<>();
        if (storico != null && !storico.isEmpty()) {
            try {
                String email = securityContext.getUserPrincipal().getName();
                User user = (User) userService.findUserByUsername(email);
                if (user.getRoles() == 1) {
                    subjects = subjectService.findAllStoricoStudent(email);
                } else if (user.getRoles() == 2) {
                    subjects = subjectService.findAllStoricoTeacher(email);
                }

            } catch (DatabaseException | UserException | SubjectBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        } else {
            try {
                subjects = subjectService.findAll();
            } catch (SubjectBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            }
        }
        return Response.ok(subjects).build();
    }
}
