package it.mytutor.business.impl;

import it.mytutor.business.exceptions.ChatBusinessException;
import it.mytutor.business.services.CreatesInterface;
import it.mytutor.domain.Creates;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.User;
import it.mytutor.domain.dao.exception.DatabaseException;
import it.mytutor.domain.dao.implement.*;
import it.mytutor.domain.dao.interfaces.*;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

public class CreatesBusiness implements CreatesInterface {

    @Override
    public void createCreates(Integer idUser1, String chatNames, Integer idUser2) throws ChatBusinessException {
        CreatesDaoInterface createsDao = new CreatesDao();
        ChatDaoInterface chatDao = new ChatDao();
        Integer idChat;
        try {
            idChat = chatDao.crateAChat(chatNames);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nella creazione della chat");
        }
        try {
            createsDao.createCreates(idUser1, idChat, idUser2);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nella creazione di creates");

        }

    }

    @Override
    public List<Creates> getCreatesByIdUser(int idUser) throws ChatBusinessException {
        List<Creates> creates = new ArrayList<>();
        List<Creates> creates2 = new ArrayList<>();
        CreatesDaoInterface createsDao = new CreatesDao();
        try {

            creates = createsDao.getCreatesByIdUser(idUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere gli oggetti creates");
        }
        for (Creates creates1: creates) {
            creates2.add(setUsersCreates(creates1));
        }

        return creates2;
    }



    private Creates setUsersCreates (Creates creates) throws ChatBusinessException {
        StudentDaoInterface studentDao = new StudentDao();
        TeacherDaoInterface teacherDao = new TeacherDao();
        UserDaoInterface userDao = new UserDao();
        User user = (User) creates.getUserListser().get(0);
        User user1 = (User) creates.getUserListser().get(1);
        List<Object> users = new ArrayList<>();
        try {
            user = userDao.getUserById(user.getIdUser());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere l'oggetto User1");
        }
        try {
            user1 = userDao.getUserById(user1.getIdUser());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ChatBusinessException("Errore nel prendere l'oggetto User2");
        }
        if (user.getRoles().equals(1)) {
            try {
                users.add(studentDao.getStudentByIdUser(user.getIdUser()));
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nel prendere l'oggetto Student");
            }
            try {
                users.add(teacherDao.getTeacherByUserID(user1.getIdUser()));
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nel prendere l'oggetto Teacher");
            }
        } else {
            try {
                users.add(studentDao.getStudentByIdUser(user1.getIdUser()));
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nel prendere l'oggetto Student");
            }
            try {
                users.add(teacherDao.getTeacherByUserID(user.getIdUser()));
            } catch (DatabaseException e) {
                e.printStackTrace();
                throw new ChatBusinessException("Errore nel prendere l'oggetto Student");
            }
        }
    creates.setUserListser(users);
    return creates;
    }
}
