package it.mytutor.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Lesson {

    private Integer idLesson;
    private String name;
    private Double price;
    private Date date;
    private String description;
    private Date publicationDate;
    private Time startTime;
    private Time endTime;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer idSubject;

    //COSTRUTTORI

    public Lesson(){
        super();
    }

    public Lesson(String name, Date date, String description, Date publicationDate, Time startTime, Time endTime) {
        super();
        this.name = name;
        this.date = date;
        this.description = description;
        this.publicationDate = publicationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lesson(String name, Double price, Date date, String description, Date publicationDate, Time startTime, Time endTime) {
        super();
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.publicationDate = publicationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Lesson(Integer idLesson, String name, Double price, Date date, String description, Date publicationDate, Time startTime, Time endTime, Timestamp createDate, Timestamp updateDate, Integer idSubject) {
        super();
        this.idLesson = idLesson;
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.publicationDate = publicationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idSubject = idSubject;
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

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Integer getIdSubject() {
        return idSubject;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(getIdLesson(), lesson.getIdLesson()) &&
                getName().equals(lesson.getName()) &&
                Objects.equals(getPrice(), lesson.getPrice()) &&
                getDate().equals(lesson.getDate()) &&
                getDescription().equals(lesson.getDescription()) &&
                getPublicationDate().equals(lesson.getPublicationDate()) &&
                getStartTime().equals(lesson.getStartTime()) &&
                getEndTime().equals(lesson.getEndTime()) &&
                Objects.equals(getCreateDate(), lesson.getCreateDate()) &&
                Objects.equals(getUpdateDate(), lesson.getUpdateDate()) &&
                Objects.equals(getIdSubject(), lesson.getIdSubject());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdLesson(), getName(), getPrice(), getDate(), getDescription(), getPublicationDate(), getStartTime(), getEndTime(), getCreateDate(), getUpdateDate(), getIdSubject());
    }


    //TOSTRING
    @Override
    public String toString() {
        return "Lesson{" +
                "idLesson=" + idLesson +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", idSubject=" + idSubject +
                '}';
    }
}
