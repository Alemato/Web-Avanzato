package it.mytutor.business.security;

import it.mytutor.business.security.securityexception.AuthenticationTokenRefreshmentException;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AuthenticationTokenService {
    /**
     * How long the token is valid for (in seconds).
     */
    private Long validFor = 31536000L;

    /**
     * How many times the token can be refreshed.
     */
    private Integer refreshLimit = 1;

    private AuthenticationTokenUtility tokenUtility = new AuthenticationTokenUtility();


    /**
     * Issue a token for a user with the given authorities.
     *
     * @param
     * @param
     * @return
     */
    public String generateToken(Object utente) throws Exception {
        if (utente instanceof Student) {
            Student student= (Student) utente;
            String username = student.getEmail();

            Set<MyRoles> authorities = new HashSet<MyRoles>();
            authorities.add(MyRoles.valueOf("STUDENT"));
            String id = generateTokenIdentifier();
            ZonedDateTime issuedDate = ZonedDateTime.now();
            ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

            AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                    .withId(id)
                    .withUsername(username)
                    .withAuthorities(authorities)
                    .withName(student.getName())
                    .withSurname(student.getSurname())
                    .withIssuedDate(issuedDate)
                    .withExpirationDate(expirationDate)
                    .withRefreshCount(0)
                    .withRefreshLimit(refreshLimit)
                    .build();

            return tokenUtility.issueToken(authenticationTokenDetails);
        } else if(utente instanceof Teacher){
            Teacher teacher= (Teacher) utente;
            String username = teacher.getEmail();

            Set<MyRoles> authorities = new HashSet<MyRoles>();
            authorities.add(MyRoles.valueOf("TEACHER"));
            String id = generateTokenIdentifier();
            ZonedDateTime issuedDate = ZonedDateTime.now();
            ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

            AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                    .withId(id)
                    .withUsername(username)
                    .withAuthorities(authorities)
                    .withName(teacher.getName())
                    .withSurname(teacher.getSurname())
                    .withIssuedDate(issuedDate)
                    .withExpirationDate(expirationDate)
                    .withRefreshCount(0)
                    .withRefreshLimit(refreshLimit)
                    .build();

            return tokenUtility.issueToken(authenticationTokenDetails);
        } else if(utente instanceof User){
            User user = (User) utente;
            String username = ((User) utente).getEmail();
            Set<MyRoles> authorities = new HashSet<MyRoles>();
            authorities.add(MyRoles.valueOf("ADMIN"));
            String id = generateTokenIdentifier();
            ZonedDateTime issuedDate = ZonedDateTime.now();
            ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

            AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                    .withId(id)
                    .withUsername(username)
                    .withAuthorities(authorities)
                    .withName(user.getName())
                    .withSurname(user.getSurname())
                    .withIssuedDate(issuedDate)
                    .withExpirationDate(expirationDate)
                    .withRefreshCount(0)
                    .withRefreshLimit(refreshLimit)
                    .build();

            return tokenUtility.issueToken(authenticationTokenDetails);

        } else throw new Exception("errore nel generare il token accunt non valido");
    }

    /**
     * Parse and validate the token.
     *
     * @param token
     * @return
     */
    public AuthenticationTokenDetails parseToken(String token) {
        return tokenUtility.parseToken(token);
    }


    /**
     * Refresh a token.
     *
     * @param currentTokenDetails
     * @return
     */
    public String refreshToken(AuthenticationTokenDetails currentTokenDetails) throws UnsupportedEncodingException {

        if (!currentTokenDetails.isEligibleForRefreshment()) {
            throw new AuthenticationTokenRefreshmentException("This token cannot be refreshed");
        }

        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(currentTokenDetails.getId()) // Reuse the same id
                .withUsername(currentTokenDetails.getUsername())
                .withAuthorities(currentTokenDetails.getAuthorities())
                .withName(currentTokenDetails.getName())
                .withSurname(currentTokenDetails.getSurname())
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(currentTokenDetails.getRefreshCount() + 1)
                .withRefreshLimit(refreshLimit)
                .build();

        return tokenUtility.issueToken(newTokenDetails);
    }

    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(validFor);
    }

    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
}
