package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.ChatDeserializer;
import it.mytutor.domain.jackson.ChatSerializer;
//import it.mytutor.domain.jackson.ChatDeserializer;
//import it.mytutor.domain.jackson.ChatSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = ChatSerializer.class)
@JsonDeserialize(using = ChatDeserializer.class)
public class Chat {
    private Integer idChat;
    private List<Object> userListser = new ArrayList<>();   // lista contenente i due user coinvolti nella chat
    private Timestamp createDate;
    private Timestamp updateDate;


    //COSTRUTTORI

    public Chat() {
        super();
    }

    public Chat(List<Object> userListser) {
        super();
        this.userListser = userListser;
    }

    public Chat(Integer idChat, List<Object> userListser, Timestamp createDate, Timestamp updateDate) {
        super();
        this.idChat = idChat;
        this.userListser = userListser;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    //GETTER

    public Integer getIdChat() {
        return idChat;
    }

    public List<Object> getUserListser() {
        return userListser;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }


    //SETTER

    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    public void setUserListser(List<Object> userListser) {
        this.userListser = userListser;
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
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return getIdChat().equals(chat.getIdChat()) &&
                getUserListser().equals(chat.getUserListser()) &&
                getCreateDate().equals(chat.getCreateDate()) &&
                getUpdateDate().equals(chat.getUpdateDate());
    }

    //HASHCODE

    @Override
    public int hashCode() {
        return Objects.hash(getIdChat(), getUserListser(), getCreateDate(), getUpdateDate());
    }


    //TOSTRING

    @Override
    public String toString() {
        return "Chat{" +
                "idChat=" + idChat +
                ", userListser=[" + this.userListser.get(0).toString() +
                ", " + this.userListser.get(1).toString() +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
