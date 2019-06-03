package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.SubjectDeserializer;
import it.mytutor.domain.jackson.SubjectSerializer;

import java.sql.Timestamp;
import java.util.Objects;

@JsonSerialize(using = SubjectSerializer.class)
@JsonDeserialize(using = SubjectDeserializer.class)
public class Subject {
    private Integer idSubject;
    private String macroSubject;
    private String microSubject;
    private Timestamp createDate;
    private Timestamp updateDate;

    //COSTRUTTORI
    public Subject() {
        super();
    }

    public Subject(String macroSubject, String microSubject) {
        super();
        this.macroSubject = macroSubject;
        this.microSubject = microSubject;
    }

    public Subject(Integer idSubject, String macroSubject, String microSubject, Timestamp createDate, Timestamp updateDate) {
        super();
        this.idSubject = idSubject;
        this.macroSubject = macroSubject;
        this.microSubject = microSubject;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    //GETTER

    public Integer getIdSubject() {
        return idSubject;
    }

    public String getMacroSubject() {
        return macroSubject;
    }

    public String getMicroSubject() {
        return microSubject;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    //SETTER

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public void setMacroSubject(String macroSubject) {
        this.macroSubject = macroSubject;
    }

    public void setMicroSubject(String microSubject) {
        this.microSubject = microSubject;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    //EQUALS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return getIdSubject().equals(subject.getIdSubject()) &&
                getMacroSubject().equals(subject.getMacroSubject()) &&
                getMicroSubject().equals(subject.getMicroSubject()) &&
                getCreateDate().equals(subject.getCreateDate()) &&
                getUpdateDate().equals(subject.getUpdateDate());
    }

    //HASHCODE

    @Override
    public int hashCode() {
        return Objects.hash(getIdSubject(), getMacroSubject(), getMicroSubject(), getCreateDate(), getUpdateDate());
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Subject{" +
                "idSubject=" + idSubject +
                ", macroSubject='" + macroSubject + '\'' +
                ", microSubject='" + microSubject + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
