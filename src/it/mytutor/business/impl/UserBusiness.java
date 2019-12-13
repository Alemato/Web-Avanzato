package it.mytutor.business.impl;

import it.mytutor.business.exceptions.UserException;
import it.mytutor.business.security.SecurityHash;
import it.mytutor.business.services.UserInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.StudentDao;
import it.mytutor.domain.dao.implement.TeacherDao;
import it.mytutor.domain.dao.implement.UserDao;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

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
            teacher.setBirthday(Date.valueOf("2015-03-31"));
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
            student.setBirthday(Date.valueOf("2015-03-31"));
            student.setLanguage(false);
            student.setImage("image");
            student.setStudyGrade("univeristario");
            return student;
        }
    }

    @Override
    public Object autentication(String username, String password) throws UserException, DatabaseException {
        Object object = new Object();
        // QUERY USER: per vedere se esiste ed di che tipo è
        UserDao userDao = new UserDao();
        User user = userDao.getUserByEmail(username);
        System.out.println(user.toString());
        System.out.println("hash da inserire :"+SecurityHash.SetHash(password));
        System.out.println("hash e giusto? "+ SecurityHash.equals(password,user) + " " + user.getPassword());
        // CONTROLLO LA PASSWORD
        if(SecurityHash.equals(password,user)){
            // QUERY SULLA TABELLA DEL TIPO
            if(user.getRoles() == 1){
                System.out.println("Studente");
                StudentDao studentDao = new StudentDao();
                Student student = studentDao.getStudentByIdUser(user.getIdUser());
                student.setUser(user);
                object = student;
                return object;
            } else if (user.getRoles() == 2){
                System.out.println("Teacher");
                TeacherDao teacherDao = new TeacherDao();
                Teacher teacher = teacherDao.getTeacherByUserID(user.getIdUser());
                teacher.setUser(user);
                object = teacher;
                return object;
            } else if (user.getRoles() == 3){
                System.out.println("Admin");
                object = user;
                return object;
            }
        } else throw new UserException("AUTENTICAZIONE NON VALIDA");

        throw new UserException("USER NON E DI NESSUN TIPO");
    }
}
