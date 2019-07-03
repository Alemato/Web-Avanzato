package it.mytutor.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Planning {
    private Integer idPlanning;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Timestamp createDate;
    private Timestamp updateDate;





    private Teacher teacher;
    private Lesson lesson;

    public Planning() { super(); }

    public Planning(Date date, Time startTime, Time endTime) {
        super();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Planning(Integer idPlanning, Date date, Time startTime, Time endTime, Timestamp createDate, Timestamp updateDate, Teacher teacher, Lesson lesson) {
        this.idPlanning = idPlanning;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.teacher = teacher;
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

    public Teacher getTeacher() {
        return teacher;
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

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", teacher=" + teacher +
                ", lesson=" + lesson +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planning planning = (Planning) o;
        return Objects.equals(idPlanning, planning.idPlanning) &&
                Objects.equals(date, planning.date) &&
                Objects.equals(startTime, planning.startTime) &&
                Objects.equals(endTime, planning.endTime) &&
                Objects.equals(createDate, planning.createDate) &&
                Objects.equals(updateDate, planning.updateDate) &&
                Objects.equals(teacher, planning.teacher) &&
                Objects.equals(lesson, planning.lesson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlanning, date, startTime, endTime, createDate, updateDate, teacher, lesson);
    }
}
