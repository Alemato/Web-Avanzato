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
    public Object findUserByUsername(String username) throws UserException, DatabaseException {
        UserDao userDao = new UserDao();
        User user = userDao.getUserByEmail(username);
        if(user.getRoles() == 1){
            StudentDao studentDao = new StudentDao();
            return studentDao.getStudentByIdUser(user.getIdUser());
        } else if (user.getRoles() == 2){

            TeacherDao teacherDao = new TeacherDao();
            return teacherDao.getTeacherByUserID(user.getIdUser());
        } else throw new UserException("USER NON TROVATO");
    }

    @Override
    public Object findUserById(String idUser) throws UserException, DatabaseException {
        UserDao userDao = new UserDao();
        User user = userDao.getUserById(Integer.parseInt(idUser));
        if(user.getRoles() == 1){
            StudentDao studentDao = new StudentDao();
            return studentDao.getStudentByIdUser(user.getIdUser());
        } else if (user.getRoles() == 2){
            TeacherDao teacherDao = new TeacherDao();
            return teacherDao.getTeacherByUserID(user.getIdUser());
        } else throw new UserException("USER NON TROVATO");
    }

    @Override
    public void editUser(Object user) throws UserException, DatabaseException {
        UserDao userDao = new UserDao();
        if(user instanceof Teacher){
            TeacherDao teacherDao = new TeacherDao();
            Teacher t = (Teacher) user;
            userDao.modifyUser(t, t.getIdUser());
            teacherDao.modifyTeacher(t);
        } else if(user instanceof Student) {
            StudentDao studentDao = new StudentDao();
            Student s = (Student) user;
            userDao.modifyUser(s, s.getIdUser());
            studentDao.modifyStudent(s);
        } else throw new UserException("USER NON VALIDO");
    }

    @Override
    public Object autentication(String username, String password) throws UserException, DatabaseException {
        Object object;
        // QUERY USER: per vedere se esiste ed di che tipo ??
        User user = (User) this.findUserByUsername(username);
        // CONTROLLO LA PASSWORD
        if(SecurityHash.equals(password,user)){
            // QUERY SULLA TABELLA DEL TIPO
            if(user.getRoles() == 1){
                StudentDao studentDao = new StudentDao();
                object = studentDao.getStudentByIdUser(user.getIdUser());
                return object;
            } else if (user.getRoles() == 2){
                TeacherDao teacherDao = new TeacherDao();
                object = teacherDao.getTeacherByUserID(user.getIdUser());
                return object;
            }
        } else throw new UserException("AUTENTICAZIONE NON VALIDA");

        throw new UserException("USER NON E DI NESSUN TIPO");
    }
}
