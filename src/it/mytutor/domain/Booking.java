package it.mytutor.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Booking {

    private Integer idBooking;
    private Date date;
    private Integer lessonState;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Student student;
    private Lesson lesson;

    //COSTRUTTORI

    public Booking() {
        super();
    }

    public Booking(Date date, Integer lessonState) {
        super();
        this.date = date;
        this.lessonState = lessonState;
    }

    public Booking(Integer idBooking, Date date, Integer lessonState, Timestamp createDate, Timestamp updateDate, Student student, Lesson lesson) {
        this.idBooking = idBooking;
        this.date = date;
        this.lessonState = lessonState;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.student = student;
        this.lesson = lesson;
    }



    //GETTER

    public Integer getIdBooking() {
        return idBooking;
    }

    public Date getDate() {
        return date;
    }

    public Integer getLessonState() {
        return lessonState;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }



    //SETTER

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLessonState(Integer lessonState) {
        this.lessonState = lessonState;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }


    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return getIdBooking().equals(booking.getIdBooking()) &&
                getDate().equals(booking.getDate()) &&
                getLessonState().equals(booking.getLessonState()) &&
                getCreateDate().equals(booking.getCreateDate()) &&
                getUpdateDate().equals(booking.getUpdateDate()) &&
                getStudent().equals(booking.getStudent()) &&
                getLesson().equals(booking.getLesson());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdBooking(), getDate(), getLessonState(), getCreateDate(), getUpdateDate(), getStudent(), getLesson());
    }



    //TOSTRING
    @Override
    public String toString() {
        return "Booking{" +
                "idBooking=" + idBooking +
                ", date=" + date +
                ", lessonState=" + lessonState +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", student=" + student.toString() +
                ", lesson=" + lesson.toString() +
                '}';
    }
}
