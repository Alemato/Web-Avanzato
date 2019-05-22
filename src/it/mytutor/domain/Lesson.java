package it.mytutor.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Lesson {

    private Long idLesson;
    private String name;
    private Double price; //TODO verificare il tipo della variabile price(LESSON)
    private Date date;
    private String description;
    private Date publicationDate; //TODO verificare il tipo della variabile publicationDate(LESSON)
    private Timestamp startTime; //TODO verificare il tipo della variabile startTime(LESSON)
    private Timestamp endTime;   //TODO verificare il tipo della variabile endTime(LESSON)
    private Long idSubject;

    //COSTRUTTORI

    public Lesson(){
        super();
    }

    public Lesson(Long idLesson, String name, Double price, Date date, String description, Date publicationDate, Timestamp startTime, Timestamp endTime, Long idSubject) {
        super();
        this.idLesson = idLesson;
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.publicationDate = publicationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.idSubject = idSubject;
    }

    //GETTER

    public Long getIdLesson() {
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Long getIdSubject() {
        return idSubject;
    }

    //SETTER

    public void setIdLesson(Long idLesson) {
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

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
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
                getDate().equals(lesson.getDate()) &&
                getDescription().equals(lesson.getDescription()) &&
                getPublicationDate().equals(lesson.getPublicationDate()) &&
                getStartTime().equals(lesson.getStartTime()) &&
                getEndTime().equals(lesson.getEndTime()) &&
                getIdSubject().equals(lesson.getIdSubject());
    }

    //HASHCODE

    @Override
    public int hashCode() {
        return Objects.hash(getIdLesson(), getName(), getPrice(), getDate(), getDescription(), getPublicationDate(), getStartTime(), getEndTime(), getIdSubject());
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
                ", idSubject=" + idSubject +
                '}';
    }
}
