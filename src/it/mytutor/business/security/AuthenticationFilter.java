package it.mytutor.business.security;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.impl.UserBusiness;
import it.mytutor.domain.User;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private UserBusiness userBusiness = new UserBusiness();

    private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String authenticationToken = authorizationHeader.substring(7);
            handleTokenBasedAuthentication(authenticationToken, requestContext);
            return;
        }

        // Other authentication schemes (such as Basic) could be supported
    }

    private void handleTokenBasedAuthentication(String authenticationToken, ContainerRequestContext requestContext) {

        AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(authenticationToken);
        try {
            User utente = (User) userBusiness.findUserByUsername(authenticationTokenDetails.getUsername());
            MyPrincipalImp authenticatedUserDetails = new MyPrincipalImp(utente);

            boolean isSecure = requestContext.getSecurityContext().isSecure();
            SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails, authenticationTokenDetails, isSecure);
            requestContext.setSecurityContext(securityContext);
        } catch (UserException e) {
            e.printStackTrace();
        }
    }

}