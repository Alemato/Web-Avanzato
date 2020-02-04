package it.mytutor.business.security;

import com.sun.istack.internal.NotNull;
import io.jsonwebtoken.*;
import it.mytutor.business.security.securityexception.InvalidAuthenticationTokenException;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationTokenUtility {
    /**
     * Secret for signing and verifying the token signature.
     */
    private String secret = "secret";

    /**
     * Allowed clock skew for verifying the token signature (in seconds).
     */
    private Long clockSkew = 10L;

    /**
     * Identifies the recipients that the JWT token is intended for.
     */
    private String audience = "http://example.org";

    /**
     * Identifies the JWT token issuer.
     */
    private String issuer = "http://example.org";

    /**
     * JWT claim for the authorities.
     */
    private String authoritiesClaimName = "authorities";

    /**
     * JWT claim for the token refreshment count.
     */
    private String refreshCountClaimName = "refreshCount";

    /**
     * JWT claim for the maximum times that a token can be refreshed.
     */
    private String refreshLimitClaimName = "refreshLimit";
    private String name = "name";
    private String surname = "surname";

    public String issueToken(AuthenticationTokenDetails authenticationTokenDetails) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(authoritiesClaimName, authenticationTokenDetails.getAuthorities())
                .claim(refreshCountClaimName, authenticationTokenDetails.getRefreshCount())
                .claim(refreshLimitClaimName, authenticationTokenDetails.getRefreshLimit())
                .claim(name, authenticationTokenDetails.getName())
                .claim(surname, authenticationTokenDetails.getSurname())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public AuthenticationTokenDetails parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .requireAudience(audience)
                    .setAllowedClockSkewSeconds(clockSkew)
                    .parseClaimsJws(token)
                    .getBody();
            Set<MyRoles> roles = extractAuthoritiesFromClaims(claims);
            return new AuthenticationTokenDetails.Builder()
                    .withId(extractTokenIdFromClaims(claims))
                    .withUsername(extractUsernameFromClaims(claims))
                    .withAuthorities(extractAuthoritiesFromClaims(claims))
                    .withName(extractName(claims))
                    .withSurname(extractSurname(claims))
                    .withIssuedDate(extractIssuedDateFromClaims(claims))
                    .withExpirationDate(extractExpirationDateFromClaims(claims))
                    .withRefreshCount(extractRefreshCountFromClaims(claims))
                    .withRefreshLimit(extractRefreshLimitFromClaims(claims))
                    .build();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidAuthenticationTokenException("Expired token", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthenticationTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        }
    }

    /**
     * Extract the token identifier from the token claims.
     *
     * @param claims
     * @return Identifier of the JWT token
     */
    private String extractTokenIdFromClaims(@NotNull Claims claims) {
        return (String) claims.get(Claims.ID);
    }

    /**
     * Extract the username from the token claims.
     *
     * @param claims
     * @return Username from the JWT token
     */
    private String extractUsernameFromClaims(@NotNull Claims claims) {
        return claims.getSubject();
    }

    /**
     * Extract the user authorities from the token claims.
     *
     * @param claims
     * @return User authorities from the JWT token
     */
    private Set<MyRoles> extractAuthoritiesFromClaims(@NotNull Claims claims) {
        List<String> rolesAsString = (List<String>) claims.getOrDefault(authoritiesClaimName, new ArrayList<>());
        return rolesAsString.stream().map(MyRoles::valueOf).collect(Collectors.toSet());
    }

    /**
     * Extract the issued date from the token claims.
     *
     * @param claims
     * @return Issued date of the JWT token
     */
    private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Extract the expiration date from the token claims.
     *
     * @param claims
     * @return Expiration date of the JWT token
     */
    private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Extract the refresh count from the token claims.
     *
     * @param claims
     * @return Refresh count from the JWT token
     */
    private int extractRefreshCountFromClaims(@NotNull Claims claims) {
        return (int) claims.get(refreshCountClaimName);
    }

    /**
     * Extract the refresh limit from the token claims.
     *
     * @param claims
     * @return Refresh limit from the JWT token
     */
    private int extractRefreshLimitFromClaims(@NotNull Claims claims) {
        return (int) claims.get(refreshLimitClaimName);
    }

    private String extractName(@NotNull Claims claims){
        return (String) claims.get(name);
    }

    private String extractSurname(@NotNull Claims claims){
        return (String) claims.get(surname);
    }
}
