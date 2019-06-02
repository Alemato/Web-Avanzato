package it.mytutor.api;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

@Path("/aut")

public class AuthenticationEndpointTEST {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = createToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        if(!username.equals("user") && !password.equals("123456789")) throw new Exception("Errore user e password non corrette");
    }

    private String createToken(String username) {
        // creazione della data di scadenza
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        // creazione token
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        token = token.concat("0username");
        return "gd0p8mqeterjhat091r61b773s0username";
    }
}
