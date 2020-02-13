package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Lesson;
import it.mytutor.domain.Teacher;
import it.mytutor.domain.dao.exception.DatabaseException;


import java.util.List;


public interface LessonDaoInterface {

    List<Lesson> getLessonsByTeacher(Teacher teacher) throws DatabaseException;

    void modifyLesson(Lesson lesson) throws DatabaseException;


    int createLesson(Lesson lesson) throws DatabaseException;

    Lesson getLessonById(Integer idLesson) throws DatabaseException;
}
