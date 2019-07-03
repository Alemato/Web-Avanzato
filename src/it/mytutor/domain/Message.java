package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.MessageDeserializer;
import it.mytutor.domain.jackson.MessageSerializer;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = MessageSerializer.class)
@JsonDeserialize(using = MessageDeserializer.class)
public class Message {
    private Integer idMessage;
    private String text;
    private Timestamp sendDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Chat chat;
    private List<User> user = new ArrayList<User>();

    //COSTRUTTORI

    public Message(){
        super();
    }

    public Message(String text, Timestamp sendDate) {
        super();
        this.text = text;
        this.sendDate = sendDate;
    }

    public Message(Integer idMessage, String text, Timestamp sendDate, Timestamp createDate, Timestamp updateDate, Chat chat, List<User> user) {
        this.idMessage = idMessage;
        this.text = text;
        this.sendDate = sendDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.chat = chat;
        this.user = user;
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

    public Chat getIdChat() {
        return chat;
    }

    public List<User> getIdUser() {
        return user;
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

    public void setIdChat(Chat chat) {
        this.chat = chat;
    }

    public void setIdUser(List<User> user) {
        this.user = user;
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

    private String userListerToString(List<User> userList){
        String userListToString="[";
        for (User user: userList) {
            userListToString = userListToString.concat(", ").concat(user.toString());
        }
        userListToString =userListToString.concat("]");
        return userListToString;
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
                ", idChat=" + chat.toString() +
                ", idUser=" + userListerToString(user) +
                '}';
    }
}
