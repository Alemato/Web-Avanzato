package it.mytutor.api;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Planning;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("lezioni")
public class LezioniRest {

    private LessonInterface lessonService = new LessonBusiness();
    private UserInterface userService = new UserBusiness();

    @GET
    @Path("teach")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response getLezioniTeachAll(SecurityContext sc) throws UserException {
        String teacherEmail = sc.getUserPrincipal().getName();
        Teacher teacher = (Teacher) userService.findUserByUsername(teacherEmail);
        List<Lesson> lessons = new ArrayList<>(lessonService.findAllLessonByTeacher(teacher));
        return Response.ok(lessons).build();
    }

    @GET
    @Path("stud")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response getLezioniStudAll(@QueryParam("macro-materia") String macroMateria,
                                      @QueryParam("nome") String nome,
                                      @QueryParam("zona") String zona,
                                      @QueryParam("micro-materia") String microMateria,
                                      @QueryParam("giorno-settimana") String giornoSettimana,
                                      @QueryParam("prezzo") String prezzo,
                                      @QueryParam("ora-inizio") String oraInizio,
                                      @QueryParam("ora-fine") String oraFine) throws DatabaseException {

        /*List<Lesson> lessons = new ArrayList<>(lessonService.findLessonByFilter(macroMateria, nome, zona,
                microMateria, giornoSettimana, prezzo, oraInizio, oraFine));*/
        return Response.ok().build();
    }
//    @GET
//    @Path("teach")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"TEACHER"})
//    public Response getLezioniTeach(@QueryParam("filtro") String filtro, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {
//
//        List<Lesson> lessons = new ArrayList<Lesson>();
//        List<Lesson> lessons20 = new ArrayList<Lesson>();
//        int index = -1;
//        String oof = "";
//
////      ############ SENZA FILTRO #############
//        if (filtro == null) {
//                Teacher teacher = new Teacher();
//                lessons.addAll(lessonService.findAllLessonByTeacher(teacher));
//        }
////      ############ CON FILTRO #############
//        else {
//                lessons.addAll(lessonService.findLessonByFilter(filtro));
//        }
////      ############ PRIMA CHIAMATA #############
//        if (numero == null && sotto == null) {
//            if (lessons.size() > 20) {
//                lessons20 = lessons.subList(0, 20);
//            }else {
//                lessons20 = lessons.subList(0, lessons.size());
//            }
//        }
////      ############ SUCCESSIVE CHIAMATE #############
//        else {
//            for (Lesson lesson : lessons) {
//                if (lesson.getIdLesson().equals(sotto)) {
//                    index = lessons.indexOf(lesson);
//                }
//            }
//            if (index < 0)
//                return Response.ok("nessun elemento").build();
//            if (lessons.size() > index + 1 + numero) {
//                lessons20 = lessons.subList(index + 1 , index + 1  + numero);
//            } else {
//                System.out.println("else");
//                if (index + 1 == lessons.size())
//                    return Response.ok("nessun elemento").build();
//                lessons20 = lessons.subList(index + 1 , lessons.size());
//            }
//        }
//        return Response.ok(lessons20).build();
//    }

//    @GET
//    @Path("stud")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"STUDENT"})
//    public Response getLezioniStud( @QueryParam("filtro") String filtro, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {
//        List<Lesson> lessons = new ArrayList<Lesson>();
//        List<Lesson> lessons20 = new ArrayList<Lesson>();
//        int index = -1;
//        String oof = "";
//
////      ############ SENZA FILTRO #############
//        if (filtro == null) {
//            lessons.addAll(lessonService.findAllLesson());
//        }
////      ############ CON FILTRO #############
//        else {
//            lessons.addAll(lessonService.findLessonByFilter(filtro));
//        }
////      ############ PRIMA CHIAMATA #############
//        if (numero == null && sotto == null) {
//            if (lessons.size() > 20) {
//                lessons20 = lessons.subList(0, 20);
//            }else {
//                lessons20 = lessons.subList(0, lessons.size());
//            }
//        }
////      ############ SUCCESSIVE CHIAMATE #############
//        else {
//            for (Lesson lesson : lessons) {
//                if (lesson.getIdLesson().equals(sotto)) {
//                    index = lessons.indexOf(lesson);
//                }
//            }
//            if (index < 0)
//                return Response.ok("nessun elemento").build();
//            if (lessons.size() > index + 1 + numero) {
//                lessons20 = lessons.subList(index + 1 , index + 1  + numero);
//            } else {
//                System.out.println("else");
//                if (index + 1 == lessons.size())
//                    return Response.ok("nessun elemento").build();
//                lessons20 = lessons.subList(index + 1 , lessons.size());
//            }
//        }
//        return Response.ok(lessons20).build();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaLezione(Planning planning) {
        System.out.println(planning);
//        Planning planning1 = lessonService.createLesson(planning);
//        System.out.println(planning1);
        return Response.ok().build();

    }


//    @Path("{LID}")
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_HTML)
//    public String getLezioniByID(@PathParam("LID") Integer lid, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {
//        return "<h1 style=\"" +
//                "color: red; " +
//                "margin: auto; " +
//                "width: fit-content; " +
//                "margin-top: 20%;\" " +
//                ">Componente Lezioni con @PathParam(\"LID\"): " + lid + ", @QueryParam(\"numero\"):" + numero + " (a default vale null) e @QueryParam(\"sotto\"):" + sotto + " (a default vale null)</h1>";
//    }

    @Path("{LID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response modificaLezione(Planning plenning, @PathParam("LID") Integer lid) {
        lessonService.updateLessson(plenning);
        return Response.ok().build();
    }

}
