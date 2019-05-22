package it.mytutor.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Booking {

    private Long idBooking;
    private Date date;
    private String lessonState;  //TODO verificare il tipo della variabile lessonState(Booking)
    private Timestamp createDate;
    private Timestamp updateDate;
    private Long idStudent;

    //COSTRUTTORI

    public Booking() {
        super();
    }

    public Booking(Date date, String lessonState) {
        super();
        this.date = date;
        this.lessonState = lessonState;
    }

    public Booking(Long idBooking, Date date, String lessonState, Timestamp createDate, Timestamp updateDate, Long idStudent) {
        super();
        this.idBooking = idBooking;
        this.date = date;
        this.lessonState = lessonState;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idStudent = idStudent;
    }



    //GETTER

    public Long getIdBooking() {
        return idBooking;
    }

    public Date getDate() {
        return date;
    }

    public String getLessonState() {
        return lessonState;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Long getIdStudent() {
        return idStudent;
    }



    //SETTER

    public void setIdBooking(Long idBooking) {
        this.idBooking = idBooking;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLessonState(String lessonState) {
        this.lessonState = lessonState;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
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
                getIdStudent().equals(booking.getIdStudent());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdBooking(), getDate(), getLessonState(), getCreateDate(), getUpdateDate(), getIdStudent());
    }


    //TOSTRING
    @Override
    public String toString() {
        return "Booking{" +
                "idBooking=" + idBooking +
                ", date=" + date +
                ", lessonState='" + lessonState + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", idStudent=" + idStudent +
                '}';
    }
}
