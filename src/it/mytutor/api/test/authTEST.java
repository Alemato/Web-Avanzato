package it.mytutor.api.test;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.business.security.AuthenticationTokenDetails;
import it.mytutor.business.security.AuthenticationTokenService;
import it.mytutor.business.security.TokenBasedSecurityContext;
import it.mytutor.business.security.securityexception.AuthenticationException;
import it.mytutor.domain.User;

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
            User utente = (User) userService.autentication(credentials.getUsername(), credentials.getPassword());
            if (utente == null) {
                throw new AuthenticationException("bad credentials");
            }
            String token = authenticationTokenService.generateToken(utente);
            AuthenticationToken authenticationToken = new AuthenticationToken(token);
            return Response.ok(authenticationToken).build();
        } catch (UserException e) {
            e.printStackTrace();
            throw new ApiWebApplicationException("Errore interno al server");
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
