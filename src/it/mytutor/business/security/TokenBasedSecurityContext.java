package it.mytutor.business.security;

import it.mytutor.domain.Ruolo;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

public class TokenBasedSecurityContext implements SecurityContext {

    private MyPrincipalImp userPrincipal;
    private AuthenticationTokenDetails authenticationTokenDetails;
    private final boolean secure;

    public TokenBasedSecurityContext(MyPrincipalImp authenticatedUserDetails, AuthenticationTokenDetails authenticationTokenDetails, boolean secure) {
        this.userPrincipal = authenticatedUserDetails;
        this.authenticationTokenDetails = authenticationTokenDetails;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isUserInRole(String s) {
        Set<MyRoles> authorities = userPrincipal.getAuthorities();
        for (MyRoles myAuthorityImpl : authorities) {
            if (myAuthorityImpl.toString().equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }

    public AuthenticationTokenDetails getAuthenticationTokenDetails() {
        return authenticationTokenDetails;
    }
}