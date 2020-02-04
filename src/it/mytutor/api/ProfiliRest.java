package it.mytutor.api;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("auth/profiles")
public class ProfiliRest {
    @Context
    private SecurityContext securityContext;
    private UserInterface userService = new UserBusiness();


    /**
     * Rest che ritorna l'oggetto di tipo Student o Teacher a seconda del suo id
     *
     * @param idUtente è l'id del oggetto richiesto
     * @return Risposta OK con oggetto chiesto
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER", "STUDENT"})
    public Response profilo(@QueryParam("id-user") String idUtente){
        Object user;
        if (idUtente == null || idUtente.isEmpty() || idUtente.equals(" ")){
            try {
                user = userService.findUserByUsername(securityContext.getUserPrincipal().getName());
            } catch (UserException | DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException(e.getMessage());
            }
        } else {
            try {
                user = userService.findUserById(idUtente);
            } catch (UserException | DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException(e.getMessage());
            }
        }
        return Response.ok(user).build();
    }

    /**
     * Rest per l'aggiornamneto dei dati del accout di tipo Student
     * @param student Oggetto modificato da caricare
     * @param hspwd hash della password del Account
     * @return Risposta custuom con stato 201 Created
     */
    @Path("/student")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"STUDENT"})
    public Response modificaStudent(Student student, @QueryParam("hspwd") String hspwd){
        String emailStudent = securityContext.getUserPrincipal().getName();
        Student student1;
        try {
            student1 = (Student) userService.findUserByUsername(emailStudent);
            if(student.getEmail().equals(emailStudent) && student1.getPassword().equals(hspwd)){
                try {
                    if (student.getPassword().equals("")) {
                        student.setPassword(student1.getPassword());
                    }
                    userService.editUser(student);
                } catch (UserException | DatabaseException e) {
                    e.printStackTrace();
                    throw new ApiWebApplicationException(e.getMessage());
                }
            } else {
                throw new ApiWebApplicationException("Errore non sei lo stesso account");
            }
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException(e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Rest per l'aggiornamneto dei dati del accout di tipo Student
     * @param teacher Oggetto modificato da caricare
     * @param hspwd hash della password del Account
     * @return Risposta custuom con stato 201 Created
     */
    @Path("/teacher")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER"})
    public Response modificaTeacher(Teacher teacher, @QueryParam("hspwd") String hspwd){
        String emailTeacher = securityContext.getUserPrincipal().getName();
        Teacher teacher1;
        try {
            teacher1 = (Teacher) userService.findUserByUsername(emailTeacher);
            if(teacher.getEmail().equals(emailTeacher) && teacher1.getPassword().equals(hspwd)){
                try {
                    if(teacher.getPassword().equals("")) {
                        teacher.setPassword(teacher1.getPassword());
                    }
                    userService.editUser(teacher);
                } catch (UserException | DatabaseException e) {
                    e.printStackTrace();
                    throw new ApiWebApplicationException(e.getMessage());
                }
            } else {
                throw new ApiWebApplicationException("Errore non sei lo stesso account", 401);
            }
        } catch (UserException | DatabaseException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException(e.getMessage());
        }
        return Response.status(Response.Status.CREATED).build();}
}
