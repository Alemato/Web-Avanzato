package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.ChatDeserializer;
import it.mytutor.domain.jackson.ChatSerializer;

import java.sql.Timestamp;
import java.util.Objects;

@JsonSerialize(using = ChatSerializer.class)
@JsonDeserialize(using = ChatDeserializer.class)
public class Chat {
    private Integer idChat;
    private String name;
    private Timestamp createDate;
    private Timestamp updateDate;


    //COSTRUTTORI

    public Chat() {
        super();
    }

    public Chat(String name) {
        super();
        this.name = name;
    }

    public Chat(Integer idChat, String name, Timestamp createDate, Timestamp updateDate) {
        super();
        this.idChat = idChat;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }



    //GETTER

    public Integer getIdChat() {
        return idChat;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
                getName().equals(chat.getName()) &&
                getCreateDate().equals(chat.getCreateDate()) &&
                getUpdateDate().equals(chat.getUpdateDate());
    }

    //HASHCODE

    @Override
    public int hashCode() {
        return Objects.hash(getIdChat(), getName(), getCreateDate(), getUpdateDate());
    }


    //TOSTRING

    @Override
    public String toString() {
        return "Chat{" +
                "idChat=" + idChat +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
