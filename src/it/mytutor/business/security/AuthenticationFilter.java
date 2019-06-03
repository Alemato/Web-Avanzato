package it.mytutor.business.security;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Estraggo heder che contine il token
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Vedo se esiste e controllo che sia formato in modo consono
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Estraggo il token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Valido il token
            validateToken(token);

        } catch (Exception e) {
            // Ritorno la risposta in caso in qui non sia valido
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        // TODO Cercare il token nel db e vedere se è scaduto nel caso sia scaduto eliminarlo
        if (!token.equals("gd0p8mqeterjhat091r61b773s0username") && !token.equals("gd0p8mqeterjhat091r61b773s1username")) throw new Exception("TOKEN NON VALIDO");
    }
}