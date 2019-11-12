package it.mytutor.api;

import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.domain.Lesson;
import it.mytutor.domain.Teacher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("auth/{SID}/lezioni")
public class LezioniRest {

    private LessonInterface lessonService = new LessonBusiness();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getLezioni(@PathParam("SID") String sid, @QueryParam("filtro") String filtro, @QueryParam("numero") Integer numero, @QueryParam("sotto") Integer sotto) {

        List<Lesson> lessons = new ArrayList<Lesson>();
        List<Lesson> lessons20 = new ArrayList<Lesson>();
        Integer index = -1;
        String oof = "";

//      ############ SENZA FILTRO #############
        if (filtro == null) {
//          ############ TEACHER #############
            if (true == true) {                                       //TODO Dal SID capire se l'utente è un Teacher
                Teacher teacher = new Teacher();
                lessons.addAll(lessonService.findAllLessonByTeacher(teacher));
//          ############ STUDENT #############
            } else if (false == false) {                              //TODO Dal SID capire se l'utente è uno Student
                lessons.addAll(lessonService.findAllLesson());
            }
        }
//      ############ CON FILTRO #############
        else {
//              ############ TEACHER #############
            if (true == true) {                                       //Da SID capire se l'utente è un Teacher
                //TODO da definire il filtro e se inserirlo per il Teacher
                lessons.addAll(lessonService.findLessonByFilter(filtro));
//              ############ STUDENT #############
            } else if (false == false) {                              //Da SID capire se l'utente è uno Student
                //TODO da definire il filtro
                lessons.addAll(lessonService.findLessonByFilter(filtro));
            }
        }
//      ############ PRIMA CHIAMATA #############
        if (numero == null && sotto == null) {
            if (lessons.size() > 20) {
                lessons20 = lessons.subList(0, 20);
            }else {
                lessons20 = lessons.subList(0, lessons.size());
            }
        }
//      ############ SUCCESSIVE CHIAMATE #############
        else {
            for (Lesson lesson : lessons) {
                if (lesson.getIdLesson().equals(sotto)) {
                    index = lessons.indexOf(lesson);
                }
            }
            if (index < 0)
                return Response.ok("nessun elemento").build();
            if (lessons.size() > index + 1 + numero) {
                lessons20 = lessons.subList(index + 1 , index + 1  + numero);
            } else {
                System.out.println("else");
                if (index + 1 == lessons.size())
                    return Response.ok("nessun elemento").build();
                lessons20 = lessons.subList(index + 1 , lessons.size());
            }
        }
        return Response.ok(lessons20).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response creaLezione(Lesson lesson) {
        Lesson lesson1 = lessonService.createLesson(lesson);
        System.out.println(lesson1);
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
    public Response modificaLezione(Lesson lesson, @PathParam("LID") Integer lid) {

        lessonService.updateLessson(lesson);
        return Response.ok().build();
    }

}
