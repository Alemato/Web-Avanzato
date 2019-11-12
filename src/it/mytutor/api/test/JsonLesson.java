package it.mytutor.api.test;

import it.mytutor.business.impl.LessonBusiness;
import it.mytutor.business.impl.MessageBusiness;
import it.mytutor.business.services.LessonInterface;
import it.mytutor.business.services.MessageInterface;
import it.mytutor.domain.Lesson;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@Path("lesson")
public class JsonLesson {

    private LessonInterface service = new LessonBusiness();

    @Path("new")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @PermitAll
    public String postNewLesson(Lesson lesson, @Context UriInfo uriInfo){
        System.out.println(lesson);


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        /*Timestamp to long*/
        //////////////////////////////////
        long tsToMil = timestamp.getTime();
        //////////////////////////////////
        System.out.println("tsToMil");
        System.out.println(tsToMil);

        long millisTimestamp = System.currentTimeMillis();
        /* long to Timestamp*/
        //////////////////////////////////
        Timestamp timestamp1 = new Timestamp(millisTimestamp);
        //////////////////////////////////
        System.out.println("timestamp1");
        System.out.println(timestamp1);

//        System.out.println(lesson.getPublicationDate());
//        long millis = lesson.getPublicationDate().getTime();
//        System.out.println(millis);
//        Date pDate = new Date(millis);
//        System.out.println(pDate);
        if (lesson.getIdLesson() != null) {
            return "<h1>" + lesson.getIdLesson() + "</h1>";
        }
        return "<h1>" + lesson.getName() + "</h1>";
    }
}
