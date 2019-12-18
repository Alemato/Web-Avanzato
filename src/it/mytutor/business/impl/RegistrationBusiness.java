package it.mytutor.business.impl;

import it.mytutor.business.exceptions.RegistrationException;
import it.mytutor.business.security.SecurityHash;
import it.mytutor.business.services.RegistrationInterface;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.StudentDao;
import it.mytutor.domain.dao.implement.TeacherDao;
import it.mytutor.domain.dao.implement.UserDao;

public class RegistrationBusiness implements RegistrationInterface {

    @Override
    public void RegistrationStudent(Student student) throws RegistrationException {
        student.setPassword(SecurityHash.SetHash(student.getPassword()));
        student.setRoles(1);
        try {
            UserDao userDao = new UserDao();
            userDao.createUser(student);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegistrationException("Errore nel creare l'user");
        }
        try {
            UserDao userDao1 = new UserDao();
            student.setUser(userDao1.getUserByEmail(student.getEmail()));
        } catch (DatabaseException e){
            e.printStackTrace();
            throw new RegistrationException("Errore nel prendere l'user creato");
        }
        try {
            StudentDao studentDao = new StudentDao();
            studentDao.createStudent(student);
        } catch (DatabaseException e){
            e.printStackTrace();
            throw new RegistrationException("Errore nel creare lo student");
        }
    }

    @Override
    public void RegistrationTeacher(Teacher teacher) throws RegistrationException {
        teacher.setPassword(SecurityHash.SetHash(teacher.getPassword()));
        teacher.setRoles(2);
        try {
            UserDao userDao = new UserDao();
            userDao.createUser(teacher);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegistrationException("Errore nel creare l'user");
        }
        try {
            UserDao userDao1 = new UserDao();
            teacher.setUser(userDao1.getUserByEmail(teacher.getEmail()));
        } catch (DatabaseException e){
            e.printStackTrace();
            throw new RegistrationException("Errore nel prendere l'user creato");
        }
        try {
            TeacherDao teacherDao = new TeacherDao();
            teacherDao.createTeacher(teacher);
        } catch (DatabaseException e){
            e.printStackTrace();
            throw new RegistrationException("Errore nel creare il teacher");
        }
    }
}
