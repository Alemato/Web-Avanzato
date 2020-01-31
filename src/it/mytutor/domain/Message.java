package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.MessageDeserializer;
import it.mytutor.domain.jackson.MessageSerializer;

import java.sql.Timestamp;
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
    private User user;

    //COSTRUTTORI

    public Message(){
        super();
    }

    public Message(String text, Timestamp sendDate) {
        super();
        this.text = text;
        this.sendDate = sendDate;
    }

    public Message(Integer idMessage, String text, Timestamp sendDate, Timestamp createDate, Timestamp updateDate, Chat chat, User user) {
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

    public Chat getChat() {
        return chat;
    }

    public User getUser() {
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

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setUser(User user) {
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
                getChat().equals(message.getChat()) &&
                getUser().equals(message.getUser());
    }


    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdMessage(), getText(), getSendDate(), getCreateDate(), getUpdateDate(), getChat(), getUser());
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
                "," + this.chat.toString() +
                "," + this.user.toString() +
                '}';
    }
}
