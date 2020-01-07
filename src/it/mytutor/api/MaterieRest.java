package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.SubjectBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.SubjectInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Subject;
import it.mytutor.domain.Teacher;
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

   /* *//**
     * rest per i menu di select di inserimento lezione e di ricerca lezione
     * @return lista di subject contenenti materie e micromaterie usate per i menu select
     *//*
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT", "TEACHER"})
    public Response getMaterie() {
        List<Subject> subjects;
        try {
            subjects = subjectService.findAll();
        } catch (SubjectBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(subjects).build();
    }*/


    /** rest per i menu di select di inserimento lezione e di ricerca lezione e per lo storico
     * @param storico parametro che identifica il fatto che si fa la richiesta dalla pagina dello storico
     * @return lista di subject contenenti materie e micromaterie usate per i menu select
     */
    @Path("all")
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

            } catch (SubjectBusinessException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
            } catch (UserException e) {
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

   /* *//**
     * Rest solo per storico
     *
     * @return lista di subject contenenti materie e micromaterie usate per i menu select
     *//*
    @Path("history")
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
    }*/

    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaMaterie(Subject subject){
        subjectService.createSubject(subject);
        return Response.ok().build();
    }*/
}
