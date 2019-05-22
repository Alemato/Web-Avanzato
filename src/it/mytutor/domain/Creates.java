package it.mytutor.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Creates {
    private Integer idCreates;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer idUser;
    private Integer idChat;

    //COSTRUTTORI

    public Creates(Integer idCreates, Timestamp createDate, Timestamp updateDate, Integer idUser, Integer idChat) {
        super();
        this.idCreates = idCreates;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idUser = idUser;
        this.idChat = idChat;
    }


    //GETTER
    public Integer getIdCreates() {
        return idCreates;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIdChat() {
        return idChat;
    }



    //SETTER
    public void setIdCreates(Integer idCreates) {
        this.idCreates = idCreates;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }



    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creates)) return false;
        Creates creates = (Creates) o;
        return getIdCreates().equals(creates.getIdCreates()) &&
                getCreateDate().equals(creates.getCreateDate()) &&
                getUpdateDate().equals(creates.getUpdateDate()) &&
                getIdUser().equals(creates.getIdUser()) &&
                getIdChat().equals(creates.getIdChat());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdCreates(), getCreateDate(), getUpdateDate(), getIdUser(), getIdChat());
    }


    //TOSTRING
    @Override
    public String toString() {
        return "Creates{" +
                "idCreates=" + idCreates +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", idUser=" + idUser +
                ", idChat=" + idChat +
                '}';
    }
}
