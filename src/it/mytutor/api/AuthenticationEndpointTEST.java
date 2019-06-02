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
        String role = null;
        // controllo la password e username
        if (username != null && password != null) {
            if (username.equals("user") && password.equals("123456789")) {
                role = "ADMIN";
            }
        }
        if (role != null) {
            return Response.ok(createToken(username, role), MediaType.TEXT_HTML).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) {
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
