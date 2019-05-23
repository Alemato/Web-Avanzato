package it.mytutor.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lesson {

    private Integer idLesson;
    private String name;
    private Double price;
    private List<Date> date = new ArrayList<Date>();
    private String description;
    private Date publicationDate;
    private List<Time> startTime = new ArrayList<Time>();
    private List<Time> endTime= new ArrayList<Time>();
    private Timestamp createDate;
    private Timestamp updateDate;
    private Subject subject;

    //COSTRUTTORI

    public Lesson(){
        super();
    }

    public Lesson(String name, Double price, String description, Date publicationDate) {
        super();
        this.name = name;
        this.price = price;
        this.description = description;
        this.publicationDate = publicationDate;
    }

    public Lesson(Integer idLesson, String name, Double price, List<Date> date, String description, Date publicationDate, List<Time> startTime, List<Time> endTime, Timestamp createDate, Timestamp updateDate, Subject subject) {
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
        this.subject = subject;
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

    public List<Date> getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public List<Time> getStartTime() {
        return startTime;
    }

    public List<Time> getEndTime() {
        return endTime;
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

    public void setDate(List<Date> date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setStartTime(List<Time> startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(List<Time> endTime) {
        this.endTime = endTime;
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
                getCreateDate().equals(lesson.getCreateDate()) &&
                getUpdateDate().equals(lesson.getUpdateDate()) &&
                getSubject().equals(lesson.getSubject());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdLesson(), getName(), getPrice(), getDate(), getDescription(), getPublicationDate(), getStartTime(), getEndTime(), getCreateDate(), getUpdateDate(), getSubject());
    }

    private String dateToString(List<Date> dateList){
        String dateString ="[";
        for (Date date: dateList){
            dateString = dateString.concat(", ").concat(date.toString());
        }
        dateString = dateString.concat("]");
        return dateString;
    }

    private String startTimeToString(List<Time> timeList){
        String startTimeString="[";
        for (Time startTime: timeList){
            startTimeString = startTimeString.concat(", ").concat(startTime.toString());
        }
        startTimeString=startTimeString.concat("]");
        return startTimeString;
    }

    private String endTimeToString(List<Time> timeList){
        String endTimeString="[";
        for (Time endTime: timeList){
            endTimeString = endTimeString.concat(", ").concat(endTime.toString());
        }
        endTimeString=endTimeString.concat("]");
        return endTimeString;
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Lesson{" +
                "idLesson=" + idLesson +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + dateToString(date) +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                ", startTime=" + startTimeToString(startTime) +
                ", endTime=" + endTimeToString(endTime) +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", subject=" + subject.toString() +
                '}';
    }
}
