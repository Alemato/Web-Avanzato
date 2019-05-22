package it.mytutor.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private Integer idMessage;
    private String text;
    private Timestamp sendDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Integer idChat;
    private Integer idUser;

    //COSTRUTTORI

    public Message(){
        super();
    }

    public Message(String text, Timestamp sendDate) {
        super();
        this.text = text;
        this.sendDate = sendDate;
    }

    public Message(Integer idMessage, String text, Timestamp sendDate, Timestamp createDate, Timestamp updateDate, Integer idChat, Integer idUser) {
        super();
        this.idMessage = idMessage;
        this.text = text;
        this.sendDate = sendDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.idChat = idChat;
        this.idUser = idUser;
    }

    //GETTER

    public Integer getIdMessage() {
        return idMessage;
    }

    public String getText() {
        return text;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public Integer getIdChat() {
        return idChat;
    }

    public Integer getIdUser() {
        return idUser;
    }


    //SETTER

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getIdMessage().equals(message.getIdMessage()) &&
                getText().equals(message.getText()) &&
                getSendDate().equals(message.getSendDate()) &&
                getCreateDate().equals(message.getCreateDate()) &&
                getUpdateDate().equals(message.getUpdateDate()) &&
                getIdChat().equals(message.getIdChat()) &&
                getIdUser().equals(message.getIdUser());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdMessage(), getText(), getSendDate(), getCreateDate(), getUpdateDate(), getIdChat(), getIdUser());
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", text='" + text + '\'' +
                ", sendDate=" + sendDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", idChat=" + idChat +
                ", idUser=" + idUser +
                '}';
    }
}
