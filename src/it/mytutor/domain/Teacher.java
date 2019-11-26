package it.mytutor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.mytutor.domain.jackson.TeacherDeserializer;
import it.mytutor.domain.jackson.TeacherSerializer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@JsonSerialize(using = TeacherSerializer.class)
@JsonDeserialize(using = TeacherDeserializer.class)
public class Teacher extends User{
    private Integer idTeacher;
    private Integer postCode;
    //TODO City non andrebbe con la minuscola?
    private String city;
    private String region;
    private String street;
    private String streetNumber;
    private String byography;
    private Timestamp crateDateTeacher;
    private Timestamp updateDateTeacher;

    //COSTRUTTORI

    public Teacher() {
        super();
    }

    /**
     * COSTRUTTORE COMPLETO
     * @param idUser
     * @param email
     * @param password
     * @param name
     * @param surname
     * @param birtday
     * @param language
     * @param image
     * @param createDate
     * @param updateDate
     * @param idTeacher
     * @param postCode
     * @param city
     * @param region
     * @param street
     * @param streetNumber
     * @param byography
     * @param crateDateTeacher
     * @param updateDateTeacher
     */
    public Teacher(Integer idUser, String email, String password, String name, String surname, Date birtday, Boolean language, String image, Timestamp createDate, Timestamp updateDate, Integer idTeacher, Integer postCode, String city, String region, String street, String streetNumber, String byography, Timestamp crateDateTeacher, Timestamp updateDateTeacher,Integer roles ) {
        super(idUser, email, roles, password, name, surname, birtday, language, image, createDate, updateDate);
        this.idTeacher = idTeacher;
        this.postCode = postCode;
        this.city = city;
        this.region = region;
        this.street = street;
        this.streetNumber = streetNumber;
        this.byography = byography;
        this.crateDateTeacher = crateDateTeacher;
        this.updateDateTeacher = updateDateTeacher;
    }


    //GETTER

    public Integer getIdTeacher() {
        return idTeacher;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getByography() {
        return byography;
    }

    public Timestamp getCrateDateTeacher() {
        return crateDateTeacher;
    }

    public Timestamp getUpdateDateTeacher() {
        return updateDateTeacher;
    }

    //SETTER

    public void setUser(User user){
        this.setIdUser(user.getIdUser());
        this.setEmail(user.getEmail());
        this.setRoles(user.getRoles());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setBirthday(user.getBirthday());
        this.setLanguage(user.getLanguage());
        this.setImage(user.getImage());
        this.setCreateDate(user.getCreateDate());
        this.setUpdateDate(user.getUpdateDate());
    }
    public void setIdTeacher(Integer idTeacher) {
        this.idTeacher = idTeacher;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setByography(String byography) {
        this.byography = byography;
    }

    public void setCrateDateTeacher(Timestamp crateDateTeacher) {
        this.crateDateTeacher = crateDateTeacher;
    }

    public void setUpdateDateTeacher(Timestamp updateDateTeacher) {
        this.updateDateTeacher = updateDateTeacher;
    }


    //EQUALS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return getIdTeacher().equals(teacher.getIdTeacher()) &&
                getPostCode().equals(teacher.getPostCode()) &&
                getCity().equals(teacher.getCity()) &&
                getRegion().equals(teacher.getRegion()) &&
                getStreet().equals(teacher.getStreet()) &&
                getStreetNumber().equals(teacher.getStreetNumber()) &&
                Objects.equals(getByography(), teacher.getByography()) &&
                getCrateDateTeacher().equals(teacher.getCrateDateTeacher()) &&
                getUpdateDateTeacher().equals(teacher.getUpdateDateTeacher());
    }

    //HASHCODE
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdTeacher(), getPostCode(), getCity(), getRegion(), getStreet(), getStreetNumber(), getByography(), getCrateDateTeacher(), getUpdateDateTeacher());
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Teacher{" +
                "idTeacher=" + idTeacher +
                ", postCode=" + postCode +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", byography='" + byography + '\'' +
                ", crateDateTeacher=" + crateDateTeacher +
                ", updateDateTeacher=" + updateDateTeacher +
                ", User{" +
                "idUser=" + this.getIdUser() +
                ", email='" + this.getEmail() + '\'' +
                ", roles=" + this.getRoles() +
                ", password='" + this.getPassword() + '\'' +
                ", name='" + this.getName() + '\'' +
                ", surname='" + this.getSurname() + '\'' +
                ", birthday=" + this.getBirthday() +
                ", language=" + this.getLanguage() +
                ", image='" + this.getImage() + '\'' +
                ", createDate=" + this.getCreateDate() +
                ", updateDate=" + this.getUpdateDate() +
                '}'+
                '}';
    }
}
