package it.mytutor.business.security;

import it.mytutor.business.security.securityexception.AccessDeniedException;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public final class MyPrincipalImp implements Principal {

    private String name;
    private Set<MyRoles> myRolesSet = new HashSet<>();

    public MyPrincipalImp(User utente){
        if (utente.getRoles() == 1){
            this.name = utente.getEmail();
            MyRoles myRoles = MyRoles.valueOf("STUDENT");
            myRolesSet.add(myRoles);
        }else if (utente.getRoles() == 2){
            this.name = utente.getEmail();
            MyRoles myRoles = MyRoles.valueOf("TEACHER");
            myRolesSet.add(myRoles);
        } else if(utente.getRoles()== 3){
            this.name = utente.getEmail();
            MyRoles myRoles = MyRoles.valueOf("ADMIN");
            myRolesSet.add(myRoles);
        } else throw new AccessDeniedException("Accesso non autorizzato a causa di un errore interno, account passato non è di nessun tipo");
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<MyRoles> getAuthorities() {
        return myRolesSet;
    }

}
