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
public class AuthorizationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        if(!token.equals("gd0p8mqeterjhat091r61b773s0username")) throw new Exception("TOKEN NON VALIDO");
    }

/*
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // check role user
        if(token.equals("gd0p8mqeterjhat091r61b773s0username")) {

            // Get the resource class which matches with the requested URL
            // Extract the roles declared by it
            Class<?> resourceClass = resourceInfo.getResourceClass();
            List<Role> classRoles = extractRoles(resourceClass);

            // Get the resource method which matches with the requested URL
            // Extract the roles declared by it
            Method resourceMethod = resourceInfo.getResourceMethod();
            List<Role> methodRoles = extractRoles(resourceMethod);

            try {

                // Check if the user is allowed to execute the method
                // The method annotations override the class annotations
                if (methodRoles.isEmpty()) {
                    if (checkPermissions(classRoles, "ADMIN"))
                        requestContext.abortWith(
                                Response.status(Response.Status.FORBIDDEN).build());
                } else {
                    if (checkPermissions(methodRoles, "ADMIN"))
                        requestContext.abortWith(
                                Response.status(Response.Status.FORBIDDEN).build());
                }

            } catch (Exception e) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }else  requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private Boolean checkPermissions(List<Role> allowedRoles,String userRole) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
        return !allowedRoles.contains(Role.valueOf(userRole));
    }*/
}

    /*@Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        // Estraggo la stringa di autorizazzione
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Controllo esistenza della stringa e se inizia con "Bearer " nel caso che non esista ritorno errore
        if( authorizationHeader==null || !authorizationHeader.startsWith("Bearer ")){
            throw new NotAuthorizedException("Non hai un token valido");
        }

        // Estraggo il token dalla stringa eliminando eventuali spazzi vuoti
        String token = authorizationHeader.substring("Bearer".length()).trim();


        try {
            // TODO estrazione del ruollo mediante il db
            Role userRole = Role.valueOf(convalidaToken(token));
            System.out.println(userRole);

            // Ottengo la classe di risorse che corrisponde all'URL richiesto
            List<Role> classRoles = extractRoles(resourceInfo.getResourceClass());

            // Ottengo il metodo di risorsa che corrisponde all'URL richiesto
            List<Role> methodRoles = extractRoles(resourceInfo.getResourceMethod());

            // controllo se i metodi esistono
            if (methodRoles.size() > 0) {
                // controllo che abbia accesso a un metodo richiesto
                if (!methodRoles.contains(userRole)) {
                    containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }

            // controllo se esistono classi
            if (classRoles.size() > 0) {
                // controllo che abbia accesso a la classe richiesta
                if (!classRoles.contains(userRole)) {
                    containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }
        } catch (Exception e){
            // se non ha le credenzialli idonee o non ha proprio le credenziali da mettere con un eventuale CATCH SU ROLE DB
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }

    // Estraggo i ruoli dall'elemento annotato
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        List<Role> roles = new ArrayList<>();
        if (annotatedElement == null) {
            return roles;
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return roles;
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private String convalidaToken(String token){
        if(token.equals("gd0p8mqeterjhat091r61b773s0username")) return "ADMIN";
        else return "";
    }
}
*/