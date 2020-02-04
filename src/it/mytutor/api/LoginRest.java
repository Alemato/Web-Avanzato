package it.mytutor.api;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.security.AuthenticationTokenService;
import it.mytutor.business.security.securityexception.AuthenticationException;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class LoginRest {
    private UserBusiness userService = new UserBusiness();
    private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getAuth(UserCredentials credentials){
        try {
            Object utente = userService.autentication(credentials.getUsername(), credentials.getPassword());
            System.out.println(utente.toString());
            String token = authenticationTokenService.generateToken(utente);
            if (utente  instanceof Student){
                Student student= (Student) utente;
                return Response.ok(student).header("X-Auth", token).header("X-User-Type", "student").build();
            }else if(utente instanceof Teacher){
                Teacher teacher= (Teacher) utente;
                return Response.ok(teacher).header("X-Auth", token).header("X-User-Type", "teacher").build();
            }else if(utente instanceof User){
                User admin = (User) utente;
                return Response.ok(admin).header("X-Auth", token).header("X-User-Type", "admin").build();
            } else throw new ApiWebApplicationException("bad credentials");
        } catch (UserException e) {
            e.printStackTrace();
            if (e.getMessage().equals("AUTENTICAZIONE NON VALIDA")){
                throw new ApiWebApplicationException("credenziali non valide", 401);
            } else throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        } catch (AuthenticationException authenticationException) {
            authenticationException.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server: "+ e.getMessage());
        }
    }
}
