package it.mytutor.api;

import it.mytutor.api.test.ApiWebApplicationException;
import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.security.SecurityHash;
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

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"TEACHER", "STUDENT"})
    public Response profilo(@QueryParam("email") String email){
        Object user;
        if (email == null || email.isEmpty() || email.equals(" ")){
            try {
                user = userService.findUserByUsername(securityContext.getUserPrincipal().getName());
            } catch (UserException | DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException(e.getMessage());
            }
        } else {
            try {
                user = userService.findUserByUsername(email);
            } catch (UserException | DatabaseException e) {
                e.printStackTrace();
                throw new ApiWebApplicationException(e.getMessage());
            }
        }
        return Response.ok(user).build();
    }

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
                    } else {
                        student.setPassword(SecurityHash.SetHash(student.getPassword()));
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
            System.out.println(teacher1.getPassword());
            System.out.println(hspwd);
            if(teacher.getEmail().equals(emailTeacher) && teacher1.getPassword().equals(hspwd)){
                try {
                    if(teacher.getPassword().equals("")){
                        teacher.setPassword(teacher1.getPassword());
                    } else {
                        teacher.setPassword(SecurityHash.SetHash(teacher.getPassword()));
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
