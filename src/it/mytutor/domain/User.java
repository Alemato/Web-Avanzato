package it.mytutor.domain;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class User {
    private Integer idUser;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Date birtday;
    private Boolean language;
    private String image;
    private Timestamp createDate;
    private Timestamp updateDate;

    //COSTRUTTORI

    public User() {
        super();
    }

    public User(String email, String password, String name, String surname, Date birtday, Boolean language) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birtday = birtday;
        this.language = language;
    }

    public User(Integer idUser, String email, String password, String name, String surname, Date birtday, Boolean language, String image, Timestamp createDate, Timestamp updateDate) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birtday = birtday;
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

    public Date getBirtday() {
        return birtday;
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

    public void setBirtday(Date birtday) {
        this.birtday = birtday;
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
                getBirtday().equals(user.getBirtday()) &&
                getLanguage().equals(user.getLanguage()) &&
                Objects.equals(getImage(), user.getImage()) &&
                getCreateDate().equals(user.getCreateDate()) &&
                getUpdateDate().equals(user.getUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getEmail(), getPassword(), getName(), getSurname(), getBirtday(), getLanguage(), getImage(), getCreateDate(), getUpdateDate());
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
                ", birtday=" + birtday +
                ", language=" + language +
                ", image='" + image + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
