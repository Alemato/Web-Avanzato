package it.mytutor.api;

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

import javax.annotation.Generated;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("lessons")
public class LezioniRest {
    @Context
    private SecurityContext securityContext;


    private LessonInterface lessonService = new LessonBusiness();
    private UserInterface userService = new UserBusiness();

    /**
     * Rest che ritorna la lista delle lezioni del professore che la richiede
     * @return Lista di Lesson
     */
    @GET
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
        } catch (LessonBusinessException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(lessons).build();
    }
    @Path("student")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getAllLezioni() {
        List<Lesson> lessons;
        try {
            lessons = new ArrayList<>(lessonService.findAllLesson());
        } catch (LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(lessons).build();
    }

    @Path("{IDL}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getLezionebyid(@PathParam("IDL") Integer idLesson) {
        Lesson lesson;
        try {
            lesson = lessonService.findLessonByID(idLesson);
        } catch (LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok(lesson).build();
    }

    /**
     * Rest che modifica la lezione inviata
     * @param lesson Lezione da modificare con le mdifiche
     * @return Risposta custom con stato 201 Created
     */
    @Path("modify")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response modificaLezione(Lesson lesson) {
        try {
            lessonService.updateLessson(lesson);
        } catch (LessonBusinessException | SubjectBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * @param lesson
     * @return
     */
    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response creaLezione(Lesson lesson) {
        String id;
        try {
            id = lessonService.createLesson(lesson).toString();
        } catch (LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.created(URI.create("http://localhost:8080/api/lessons/" + id)).build();
    }

    @Path("noplanning")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getLezioniNoPlanning() {
        String teacherEmail = securityContext.getUserPrincipal().getName();
        List<Lesson> lessons = new ArrayList<>();
        Teacher teacher;
        try {
            teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        try {
            lessons = lessonService.findlessonWithoutPlanningByTeacher(teacher);
        } catch (DatabaseException | LessonBusinessException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: " + e.getMessage());
        }
        return Response.ok(lessons).build();
    }
}
