package it.mytutor.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Booking {

    private Integer idBooking;
    private Date date;
    private boolean lessonState;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer idStudent;
    private Integer idLesson;

    //COSTRUTTORI

    public Booking() {
        super();
    }

    public Booking(Date date, boolean lessonState) {
        super();
        this.date = date;
        this.lessonState = lessonState;
    }

    public Booking(Integer idBooking, Date date, boolean lessonState, Timestamp createDate, Timestamp updateDate, Integer idStudent, Integer idLesson) {
        super();
        this.idBooking = idBooking;
        this.date = date;
        this.lessonState = lessonState;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idStudent = idStudent;
        this.idLesson = idLesson;
    }

    //GETTER

    public Integer getIdBooking() {
        return idBooking;
    }

    public Date getDate() {
        return date;
    }

    public boolean isLessonState() {
        return lessonState;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public Integer getIdLesson() {
        return idLesson;
    }

    //SETTER

    public void setIdBooking(Integer idBooking) {
        this.idBooking = idBooking;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLessonState(boolean lessonState) {
        this.lessonState = lessonState;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public void setIdLesson(Integer idLesson) {
        this.idLesson = idLesson;
    }


    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return isLessonState() == booking.isLessonState() &&
                getIdBooking().equals(booking.getIdBooking()) &&
                getDate().equals(booking.getDate()) &&
                getCreateDate().equals(booking.getCreateDate()) &&
                getUpdateDate().equals(booking.getUpdateDate()) &&
                getIdStudent().equals(booking.getIdStudent()) &&
                getIdLesson().equals(booking.getIdLesson());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdBooking(), getDate(), isLessonState(), getCreateDate(), getUpdateDate(), getIdStudent(), getIdLesson());
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
                ", idStudent=" + idStudent +
                ", idLesson=" + idLesson +
                '}';
    }
}
