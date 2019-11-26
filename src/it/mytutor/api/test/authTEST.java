package it.mytutor.api.test;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.security.AuthenticationTokenDetails;
import it.mytutor.business.security.AuthenticationTokenService;
import it.mytutor.business.security.TokenBasedSecurityContext;
import it.mytutor.business.security.securityexception.AuthenticationException;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;

@Path("authentication-1")
public class authTEST {
    private UserBusiness userService = new UserBusiness();

    private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();

    /**
     * Validate user credentials and issue a token for the user.
     *
     * @param credentials
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response authenticate(UserCredentials credentials) {
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
            throw new ApiWebApplicationException("Errore interno al server");
        }
    }

    /**
     * Refresh the authentication token for the current user.
     *
     * @return
     */
    @POST
    @Path("refresh")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response refresh(@Context ContainerRequestContext context) throws UnsupportedEncodingException {
        AuthenticationTokenDetails tokenDetails = ((TokenBasedSecurityContext) context.getSecurityContext())
                .getAuthenticationTokenDetails();
        String token = authenticationTokenService.refreshToken(tokenDetails);

        AuthenticationToken authenticationToken = new AuthenticationToken(token);
        return Response.ok(authenticationToken).build();
    }
}
