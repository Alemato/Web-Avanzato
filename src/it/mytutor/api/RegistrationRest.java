package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.RegistrationException;
import it.mytutor.business.impl.RegistrationBusiness;
import it.mytutor.business.services.RegistrationInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.Timestamp;

@Path("registration")
public class RegistrationRest {
    private RegistrationInterface registration = new RegistrationBusiness();
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @PermitAll
    public String getRegistration(){
        return "<h1 style=\"" +
                "color: red; "+
                "margin: auto; " +
                "width: fit-content; " +
                "margin-top: 20%;\" " +
                ">Componente Registration</h1>";
    }

    @Path("student")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response registrationStudent(Student student){
        try {
            registration.RegistrationStudent(student);
        } catch (RegistrationException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok().build();
    }

    @Path("teacher")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response registrationTeacher(Teacher teacher){
        try {
            registration.RegistrationTeacher(teacher);
        } catch (RegistrationException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
        return Response.ok().build() ;
    }

}
