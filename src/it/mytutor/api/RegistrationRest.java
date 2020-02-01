package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.RegistrationException;
import it.mytutor.business.impl.RegistrationBusiness;
import it.mytutor.business.services.RegistrationInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("registration")
public class RegistrationRest {
    private RegistrationInterface registration = new RegistrationBusiness();

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
