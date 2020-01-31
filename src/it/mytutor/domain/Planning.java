package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.PlanningDeserializer;
import it.mytutor.domain.jackson.PlanningSerializer;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@JsonSerialize(using = PlanningSerializer.class)
@JsonDeserialize(using = PlanningDeserializer.class)
public class Planning {
    private Integer idPlanning;
    private Date date;
    private Time startTime;
    private Time endTime;
    private boolean available;
    private boolean repeatPlanning;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Lesson lesson;

    public Planning() { super(); }

    public Planning(Date date, Time startTime, Time endTime, boolean available, boolean repeatPlanning) {
        super();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
        this.repeatPlanning = repeatPlanning;
    }

    public Planning(Integer idPlanning, Date date, Time startTime, Time endTime, boolean available, boolean repeatPlanning, Timestamp createDate, Timestamp updateDate, Lesson lesson) {
        this.idPlanning = idPlanning;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
        this.repeatPlanning = repeatPlanning;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.lesson = lesson;
    }



    //GETTER

    public Integer getIdPlanning() {
        return idPlanning;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public boolean getAvailable() {return available; }

    public boolean getRepeatPlanning() {
        return repeatPlanning;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }



    public Lesson getLesson() {
        return lesson;
    }



    //SETTER

    public void setIdPlanning(Integer idPlanning) {
        this.idPlanning = idPlanning;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setAvailable(boolean available) { this.available = available; }

    public void setRepeatPlanning(boolean repeatPlanning) {
        this.repeatPlanning = repeatPlanning;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }



    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "idPlanning=" + idPlanning +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", available=" + available +
                ", repeatPlanning" + repeatPlanning +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                "," + this.lesson +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planning)) return false;
        Planning planning = (Planning) o;
        return getAvailable() == planning.getAvailable() &&
                getRepeatPlanning() == planning.getRepeatPlanning() &&
                Objects.equals(getIdPlanning(), planning.getIdPlanning()) &&
                getDate().equals(planning.getDate()) &&
                getStartTime().equals(planning.getStartTime()) &&
                getEndTime().equals(planning.getEndTime()) &&
                Objects.equals(getCreateDate(), planning.getCreateDate()) &&
                Objects.equals(getUpdateDate(), planning.getUpdateDate()) &&
                Objects.equals(getLesson(), planning.getLesson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPlanning(), getDate(), getStartTime(), getEndTime(), getAvailable(), getRepeatPlanning(), getCreateDate(), getUpdateDate(), getLesson());
    }
}
