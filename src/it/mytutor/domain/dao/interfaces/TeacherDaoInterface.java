package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;

public interface TeacherDaoInterface {
     void createTeacher(Teacher teacher)throws DatabaseException;

     void modifyTeacher(Teacher teacher)throws DatabaseException;

     Teacher getTeacherByID(int id) throws DatabaseException;

     Teacher getTeacherByUserID(int userId) throws DatabaseException;

     List<Teacher> getAllTeacher()throws DatabaseException;
}
