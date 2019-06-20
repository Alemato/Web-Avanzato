package it.mytutor.business.security;

import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public final class MyPrincipalImp implements Principal {

    private String name;
    private Set<MyRoles> myRolesSet = new HashSet<>();

    public MyPrincipalImp(Teacher utente){
        this.name = utente.getName();
        MyRoles myRoles = MyRoles.valueOf("TEACHER");
        myRolesSet.add(myRoles);
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<MyRoles> getAuthorities() {
        return myRolesSet;
    }

}
