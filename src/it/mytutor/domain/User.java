package it.mytutor.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@JsonSerialize(using = UserSerializer.class)
//@JsonDeserialize(using = UserDeserializer.class)
public class User {
    private Integer idUser;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Date birthday;
    private Boolean language;
    private String image;
    private Timestamp createDate;
    private Timestamp updateDate;

    //COSTRUTTORI

    public User() {
        super();
    }

    public User(String email, String password, String name, String surname, Date birthday, Boolean language) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.language = language;
    }

    public User(Integer idUser, String email, String password, String name, String surname, Date birthday, Boolean language, String image, Timestamp createDate, Timestamp updateDate) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.language = language;
        this.image = image;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    //GETTER

    public Integer getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Boolean getLanguage() {
        return language;
    }

    public String getImage() {
        return image;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    //SETTER

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirtday(Date birthday) {
        this.birthday = birthday;
    }

    public void setLanguage(Boolean language) {
        this.language = language;
    }

    public void setImage(String image) {
        this.image = image;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getIdUser().equals(user.getIdUser()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getName().equals(user.getName()) &&
                getSurname().equals(user.getSurname()) &&
                getBirthday().equals(user.getBirthday()) &&
                getLanguage().equals(user.getLanguage()) &&
                Objects.equals(getImage(), user.getImage()) &&
                getCreateDate().equals(user.getCreateDate()) &&
                getUpdateDate().equals(user.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getEmail(), getPassword(), getName(), getSurname(), getBirthday(), getLanguage(), getImage(), getCreateDate(), getUpdateDate());
    }


    //TOSTRING

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", language=" + language +
                ", image='" + image + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
