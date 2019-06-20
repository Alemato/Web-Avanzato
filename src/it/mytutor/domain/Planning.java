package it.mytutor.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Planning {
    private Integer idPlanning;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Lesson lesson;

    public Planning() { super(); }

    public Planning(Date date, Time startTime, Time endTime) {
        super();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Planning(Integer idPlanning, Date date, Time startTime, Time endTime, Timestamp createDate, Timestamp updateDate, Lesson lesson) {
        this.idPlanning = idPlanning;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
