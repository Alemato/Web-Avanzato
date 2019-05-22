package it.mytutor.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Student {

    private Integer idStudent;
    private String studyGrade;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer idUser;

    //COSTRUTTORI

    public Student() {
        super();
    }

    public Student(String studyGrade) {
        super();
        this.studyGrade = studyGrade;
    }

    public Student(Integer idStudent, String studyGrade, Timestamp createDate, Timestamp updateDate, Integer idUser) {
        super();
        this.idStudent = idStudent;
        this.studyGrade = studyGrade;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idUser = idUser;
    }

    //GETTER

    public Integer getIdStudent() {
        return idStudent;
    }

    public String getStudyGrade() {
        return studyGrade;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    //SETTER

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public void setStudyGrade(String studyGrade) {
        this.studyGrade = studyGrade;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }


    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getIdStudent().equals(student.getIdStudent()) &&
                Objects.equals(getStudyGrade(), student.getStudyGrade()) &&
                getCreateDate().equals(student.getCreateDate()) &&
                getUpdateDate().equals(student.getUpdateDate()) &&
                getIdUser().equals(student.getIdUser());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdStudent(), getStudyGrade(), getCreateDate(), getUpdateDate(), getIdUser());
    }


    //TOSTRING

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", studyGrade='" + studyGrade + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", getIdUser=" + idUser +
                '}';
    }
}
