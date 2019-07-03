package it.mytutor.domain.dao.interfaces;

import it.mytutor.domain.Planning;
import it.mytutor.domain.Student;
import it.mytutor.domain.dao.exception.DatabaseException;

import java.util.List;

public interface PlanningDaoInterface {
    void updatePlanning(Planning planning) throws DatabaseException;
    void createPlanning(Planning planning) throws DatabaseException;
    Planning getPlanningByID(int id) throws DatabaseException;
    Planning getPlanningByStudent(Student student) throws DatabaseException;
    List<Planning> getAllPlanningOfStudent(Student student) throws DatabaseException;
}
