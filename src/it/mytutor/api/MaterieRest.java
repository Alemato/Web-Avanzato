package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.impl.SubjectBusiness;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.domain.Subject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("materie")
public class MaterieRest {
    @Context
    private SecurityContext securityContext;


    private SubjectInterface subjectService = new SubjectBusiness();

    /**
     * @return lista di subject contenenti materie e micromaterie usate per i menu select
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getMaterie() {
        List<Subject> subjects;
        try {
            subjects = subjectService.findAll();
        } catch (SubjectBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(subjects).build();
    }
    @Path("storico")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getMaterieStoriche() {
        List<Subject> subjects;
        String email = securityContext.getUserPrincipal().getName();
        try {
            subjects = subjectService.findAllStorico(email);
        } catch (SubjectBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(subjects).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaMaterie(Subject subject){
        subjectService.createSubject(subject);
        return Response.ok().build();
    }
}
