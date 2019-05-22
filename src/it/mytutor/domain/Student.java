package it.mytutor.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Student {

    private Long idStudent;
    private String studyGrade;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Long idUser;

    //COSTRUTTORI

    public Student() {
        super();
    }

    public Student(String studyGrade) {
        super();
        this.studyGrade = studyGrade;
    }

    public Student(Long idStudent, String studyGrade, Timestamp createDate, Timestamp updateDate, Long idUser) {
        super();
        this.idStudent = idStudent;
        this.studyGrade = studyGrade;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idUser = idUser;
    }

    //GETTER

    public Long getIdStudent() {
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

    public Long getIdUser() {
        return idUser;
    }

    //SETTER

    public void setIdStudent(Long idStudent) {
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

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


    //EQUALS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getIdStudent().equals(student.getIdStudent()) &&
                getStudyGrade().equals(student.getStudyGrade()) &&
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
