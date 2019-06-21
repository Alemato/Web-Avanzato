package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@JsonSerialize(using = CreatesSerializer.class)
//@JsonDeserialize(using = CreatesDeserializer.class)
public class Creates {
    private Integer idCreates;
    private String name;
    private Timestamp createDate;
    private Timestamp updateDate;
    private List<User> userListser = new ArrayList<User>();   // lista contenente i due user coinvolti nella chat
    private Chat chat;

    //COSTRUTTORI
    public Creates() {
        super();
    }

     public Creates(String name) {
        super();
        this.name = name;
    }

    public Creates(Integer idCreates, String name, Timestamp createDate, Timestamp updateDate, List<User> userListser, Chat chat) {
        this.idCreates = idCreates;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userListser = userListser;
        this.chat = chat;
    }

    //GETTER
    public Integer getIdCreates() {
        return idCreates;
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

    public List<User> getUserListser() {
        return userListser;
    }

    public Chat getChat() {
        return chat;
    }



    //SETTER
    public void setIdCreates(Integer idCreates) {
        this.idCreates = idCreates;
    }

    public void setName(String name) {
        name = name;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public void setUserListser(List<User> userListser) {
        this.userListser = userListser;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    //AGGIUNGI USER
    public void addUserInList(User user){
        this.userListser.add(user);
    }

    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creates)) return false;
        Creates creates = (Creates) o;
        return getIdCreates().equals(creates.getIdCreates()) &&
                getName().equals(creates.getName()) &&
                getCreateDate().equals(creates.getCreateDate()) &&
                getUpdateDate().equals(creates.getUpdateDate()) &&
                getUserListser().equals(creates.getUserListser()) &&
                getChat().equals(creates.getChat());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(getIdCreates(), getName(), getCreateDate(), getUpdateDate(), getUserListser(), getChat());
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
        return "Creates{" +
                "idCreates=" + idCreates +
                ", Name='" + name +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", userListser=" + userListerToString(this.userListser) +
                ", chat=" + chat.toString() +
                '}';
    }
}
