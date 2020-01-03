package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.LessonDeserializer;
import it.mytutor.domain.jackson.LessonSerializer;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = LessonSerializer.class)
@JsonDeserialize(using = LessonDeserializer.class)
public class Lesson {

    private Integer idLesson;
    private String name;
    private Double price;
    private String description;
    private Date publicationDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Subject subject;
    private Teacher teacher;

    //COSTRUTTORI
    public Lesson(){
    }

    public Lesson(Integer idLesson, String name, Double price,
                  String description, Date publicationDate,
                  Timestamp createDate, Timestamp updateDate, Subject subject, Teacher teacher) {
        this.idLesson = idLesson;
        this.name = name;
        this.price = price;
        this.description = description;
        this.publicationDate = publicationDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.subject = subject;
        this.teacher=teacher;
    }

    //GETTER

    public Integer getIdLesson() {
        return idLesson;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    //SETTER

    public void setIdLesson(Integer idLesson) {
        this.idLesson = idLesson;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return getIdLesson().equals(lesson.getIdLesson()) &&
                getName().equals(lesson.getName()) &&
                getPrice().equals(lesson.getPrice()) &&
                getDescription().equals(lesson.getDescription()) &&
                getPublicationDate().equals(lesson.getPublicationDate()) &&
                getCreateDate().equals(lesson.getCreateDate()) &&
                getUpdateDate().equals(lesson.getUpdateDate()) &&
                getSubject().equals(lesson.getSubject()) &&
                getTeacher().equals(lesson.getTeacher());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdLesson(), getName(), getPrice(),
                getDescription(), getPublicationDate(),
                getCreateDate(), getUpdateDate(), getSubject(), getTeacher());
    }


    //TOSTRING
    @Override
    public String toString() {
        return "Lesson{" +
                "idLesson=" + idLesson +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", " + this.subject.toString() +
                ", " + this.teacher.toString() +
                '}';
    }
}
