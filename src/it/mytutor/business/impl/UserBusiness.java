package it.mytutor.business.impl;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

public class UserBusiness implements UserInterface {

    @Override
    public Object findUserByUsername(String username) throws UserException {
        // TODO QUERY USER seguita da QUERY TIPO ACCOUNT ritorna utente
        if (username.equals("mario")){
            Teacher teacher = new Teacher();
            teacher.setEmail(username);
            teacher.setRoles(2);
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
            student.setRoles(1);
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
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        //TODO QUERY USER: per vedere se esiste ed di che tipo è
        //TODO QUERY SULLA TABELLA DEL TIPO
        if(username.equals("mario")){
            Teacher teacher = new Teacher();
            teacher.setIdUser(1);
            teacher.setEmail(username);
            teacher.setPassword(password);
            teacher.setRoles(2);
            teacher.setName("Mario");
            teacher.setSurname("Rossi");
            teacher.setBirtday(Date.valueOf("2015-03-31"));
            teacher.setLanguage(true);
            teacher.setImage("image");
            teacher.setCreateDate(new Timestamp(System.currentTimeMillis()));
            teacher.setUpdateDate(new Timestamp(System.currentTimeMillis()));

            teacher.setIdTeacher(1);
            teacher.setPostCode(1234);
            teacher.setCity("Roma");
            teacher.setRegion("Lazio");
            teacher.setStreet("Via roma");
            teacher.setStreetNumber("1");
            teacher.setByography("SONO MARIO");
            teacher.setCrateDateTeacher(new Timestamp(System.currentTimeMillis()));
            teacher.setUpdateDateTeacher(new Timestamp(System.currentTimeMillis()));

            object = teacher;
            return object;
        }
        else if (username.equals("marco")){
            Student student = new Student();
            student.setIdUser(1);
            student.setEmail(username);
            student.setRoles(1);
            student.setCreateDate(new Timestamp(System.currentTimeMillis()));
            student.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            student.setPassword(password);
            student.setName("Marco");
            student.setSurname("Bianchi");
            student.setBirtday(Date.valueOf("2015-03-31"));
            student.setLanguage(false);
            student.setImage("image");
            student.setIdStudent(1);
            student.setStudyGrade("univeristario");
            student.setCreateDateStudent(new Timestamp(System.currentTimeMillis()));
            student.setUpdateDateStudent(new Timestamp(System.currentTimeMillis()));
            object = student;
            System.out.println(object.toString());
            return object;
        } else if(username.equals("admin")){
            user.setIdUser(1);
            user.setRoles(1);
            user.setName("admin");
            user.setSurname("admin");
            user.setBirtday(Date.valueOf("2015-03-31"));
            user.setLanguage(false);
            user.setImage("image");
            user.setCreateDate(new Timestamp(System.currentTimeMillis()));
            user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            object=user;
            return object;
        } else throw new UserException("AUTENTICAZIONE NON VALIDA USER NON E DI NESSUN TIPO");
    }
}
