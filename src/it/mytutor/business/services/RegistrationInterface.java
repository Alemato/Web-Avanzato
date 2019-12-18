package it.mytutor.business.services;

import it.mytutor.business.exceptions.RegistrationException;
import it.mytutor.domain.Student;
import it.mytutor.domain.Teacher;

public interface RegistrationInterface {
    void RegistrationStudent(Student student) throws RegistrationException;
    void RegistrationTeacher(Teacher teacher) throws RegistrationException;
}
