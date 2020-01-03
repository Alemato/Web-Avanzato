package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.LessonBusinessException;
import it.mytutor.business.exceptions.SubjectBusinessException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("lezioni")
public class LezioniRest {
    @Context
    private SecurityContext securityContext;


    private LessonInterface lessonService = new LessonBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest per la lista delle lezioni della pagina "Lista Annunci" del Professore
     * @return Lista di Lesson
     */
    @GET
    @Path("teach")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getLezioniTeachAll() {
        String teacherEmail = securityContext.getUserPrincipal().getName();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        List<Lesson> lessons;
        try {
            lessons = new ArrayList<>(lessonService.findAllLessonByTeacher(teacher));
        } catch (LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(lessons).build();
    }

    /**
     * Rest per modificare la lezione da parte del professore
     * @param lesson Lezione dal client
     * @return Status accettato con messaggio "Lezione modificata"
     */
    @Path("{LID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response modificaLezione(Lesson lesson) {
        try {
            lessonService.updateLessson(lesson);
        } catch (LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        } catch (SubjectBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(Response.Status.ACCEPTED).entity("Lezione modificata").build();
    }

}
