package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Student;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;

public interface StudentDaoInterface {

     void createStudent(Student student)throws DatabaseException;

     void modifyStudent(Student student)throws DatabaseException;

     Student getStudentByID(int id)throws DatabaseException;

     Student getStudentByIdUser (int id)throws DatabaseException;

     List<Student> getAllStudent()throws DatabaseException;


}
