package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.StudentDeserializer;
import it.mytutor.domain.jackson.StudentSerializer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@JsonSerialize(using = StudentSerializer.class)
@JsonDeserialize(using = StudentDeserializer.class)
public class Student extends User {

    private Integer idStudent;
    private String studyGrade;
    private Timestamp createDateStudent;
    private Timestamp updateDateStudent;

    //COSTRUTTORI

    public Student() {
        super();
    }

    /**
     * COSTRUTTORE SENZA ID USER E ID STUDENTE E TIMESTAMP
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param birtday
     * @param language
     * @param studyGrade
     */
    public Student(String email, String password, String name, String surname, Date birtday, Boolean language, String studyGrade) {
        super(email, password, name, surname, birtday, language);
        this.studyGrade = studyGrade;
    }


    /**
     * COSTRUTTORE SENZA ID USER E TIMESTAMP USER E TUTTO STUDENTE
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param birtday
     * @param language
     * @param idStudent
     * @param studyGrade
     * @param createDateStudent
     * @param updateDateStudent
     */
    public Student(String email, String password, String name, String surname, Date birtday, Boolean language, Integer idStudent, String studyGrade, Timestamp createDateStudent, Timestamp updateDateStudent) {
        super(email, password, name, surname, birtday, language);
        this.idStudent = idStudent;
        this.studyGrade = studyGrade;
        this.createDateStudent = createDateStudent;
        this.updateDateStudent = updateDateStudent;
    }

    /**
     * COSTRUTTORE CON TUTTI I DATI
     * @param idUser
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param birtday
     * @param language
     * @param image
     * @param createDate
     * @param updateDate
     * @param idStudent
     * @param studyGrade
     * @param createDateStudent
     * @param updateDateStudent
     */
    public Student(Integer idUser, String email, String password, String name, String surname, Date birtday, Boolean language, String image, Timestamp createDate, Timestamp updateDate, Integer idStudent, String studyGrade, Timestamp createDateStudent, Timestamp updateDateStudent) {
        super(idUser, email, password, name, surname, birtday, language, image, createDate, updateDate);
        this.idStudent = idStudent;
        this.studyGrade = studyGrade;
        this.createDateStudent = createDateStudent;
        this.updateDateStudent = updateDateStudent;
    }


    //GETTER

    public Integer getIdStudent() {
        return idStudent;
    }

    public String getStudyGrade() {
        return studyGrade;
    }

    public Timestamp getCreateDateStudent() {
        return createDateStudent;
    }

    public Timestamp getUpdateDateStudent() {
        return updateDateStudent;
    }


    //SETTER

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public void setStudyGrade(String studyGrade) {
        this.studyGrade = studyGrade;
    }

    public void setCreateDateStudent(Timestamp createDateStudent) {
        this.createDateStudent = createDateStudent;
    }

    public void setUpdateDateStudent(Timestamp updateDateStudent) {
        this.updateDateStudent = updateDateStudent;
    }

    //EQUALS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return getIdStudent().equals(student.getIdStudent()) &&
                getStudyGrade().equals(student.getStudyGrade()) &&
                getCreateDateStudent().equals(student.getCreateDateStudent()) &&
                getUpdateDateStudent().equals(student.getUpdateDateStudent());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdStudent(), getStudyGrade(), getCreateDateStudent(), getUpdateDateStudent());
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", studyGrade='" + studyGrade + '\'' +
                ", createDateStudent=" + createDateStudent +
                ", updateDateStudent=" + updateDateStudent +
                '}';
    }
}
