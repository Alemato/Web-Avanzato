package it.mytutor.business.impl;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import java.sql.Date;

public class UserBusiness implements UserInterface {

    @Override
    public Object findUserByUsername(String username) throws UserException {
        // TODO QUERY USER seguita da QUERY TIPO ACCOUNT ritorna utente
        if (username.equals("mario")){
            Teacher teacher = new Teacher();
            teacher.setEmail(username);
            teacher.setPassword("password");
            teacher.setName("Mario");
            teacher.setSurname("Rossi");
            teacher.setBirtday(Date.valueOf("2015-03-31"));
            teacher.setImage("image");
            teacher.setLanguage(true);
            teacher.setPostCode(1234);
            teacher.setCity("Roma");
            teacher.setRegion("Lazio");
            teacher.setStreet("Via roma");
            teacher.setStreetNumber("1");
            teacher.setByography("SONO MARIO");
            return teacher;
        } else {
            Student student = new Student();
            student.setEmail(username);
            student.setPassword("password");
            student.setName("Marco");
            student.setSurname("Bianchi");
            student.setBirtday(Date.valueOf("2015-03-31"));
            student.setLanguage(false);
            student.setImage("image");
            student.setStudyGrade("univeristario");
            return student;
        }
    }

    @Override
    public Object autentication(String username, String password) throws UserException {
        Object object;
        User user = new User();
        user.setEmail(username);
        user.setPassword(password);
        //TODO QUERY USER: per vedere se esiste ed di che tipo è
        //TODO QUERY SULLA TABELLA DEL TIPO
        if(username.equals("mario")){
            Teacher teacher = new Teacher();
            teacher.setEmail(username);
            teacher.setPassword(password);
            teacher.setName("Mario");
            teacher.setSurname("Rossi");
            teacher.setBirtday(Date.valueOf("2015-03-31"));
            teacher.setImage("image");
            teacher.setLanguage(true);
            teacher.setPostCode(1234);
            teacher.setCity("Roma");
            teacher.setRegion("Lazio");
            teacher.setStreet("Via roma");
            teacher.setStreetNumber("1");
            teacher.setByography("SONO MARIO");
            object = teacher;
            return object;
        }
        else if (username.equals("marco")){
            Student student = new Student();
            student.setEmail(username);
            student.setPassword(password);
            student.setName("Marco");
            student.setSurname("Bianchi");
            student.setBirtday(Date.valueOf("2015-03-31"));
            student.setLanguage(false);
            student.setImage("image");
            student.setStudyGrade("univeristario");
            object = student;
            return object;
        } else if(username.equals("admin")){
            object=user;
            return object;
        } else throw new UserException("AUTENTICAZIONE NON VALIDA USER NON E DI NESSUN TIPO");
    }
}
