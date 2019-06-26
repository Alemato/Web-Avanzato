package it.mytutor.business.security;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationTokenDetails {
    private final String id;
    private final String username;
    private final Set<MyRoles> authorities;
    private final String name;
    private final String surname;
    private final String birtday;
    private final String language;
    private final String image;
    private final Integer postCode;
    private final String city;
    private final String region;
    private final String street;
    private final String streetNumber;
    private final String byography;
    private final String studyGrade;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;
    private final int refreshCount;
    private final int refreshLimit;

    private AuthenticationTokenDetails(String id, String username, Set<MyRoles> authorities, String name, String surname, String birtday, String language, String image, Integer postCode, String city, String region, String street, String streetNumber, String byography, String studyGrade, ZonedDateTime issuedDate, ZonedDateTime expirationDate, int refreshCount, int refreshLimit) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.name = name;
        this.surname = surname;
        this.birtday = birtday.toString();
        this.language = language.toString();
        this.image = image;
        this.postCode = postCode;
        this.city = city;
        this.region = region;
        this.street = street;
        this.streetNumber = streetNumber;
        this.byography = byography;
        this.studyGrade = studyGrade;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.refreshCount = refreshCount;
        this.refreshLimit = refreshLimit;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<MyRoles> getAuthorities() {
        return authorities;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirtday() {
        return Date.valueOf(birtday);
    }

    public Boolean getLanguage() {
        return Boolean.valueOf(language);
    }

    public String getImage() {
        return image;
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

    public String getStudyGrade() {
        return studyGrade;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public int getRefreshLimit() {
        return refreshLimit;
    }

    public boolean isEligibleForRefreshment() {
        return refreshCount < refreshLimit;
    }

    public static class Builder {
        private String id;
        private String username;
        private Set<MyRoles> authorities;
        private String name;
        private String surname;
        private String birtday;
        private String language;
        private String image;
        private Integer postCode;
        private String city;
        private String region;
        private String street;
        private String streetNumber;
        private String byography;
        private String studyGrade;
        private ZonedDateTime issuedDate;
        private ZonedDateTime expirationDate;
        private int refreshCount;
        private int refreshLimit;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withAuthorities(Set<MyRoles> authorities) {
            this.authorities = Collections.unmodifiableSet(authorities == null ? new HashSet<>() : authorities);
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname){
            this.surname=surname;
            return this;
        }

        public Builder withBirtday(String date){
            this.birtday=date.toString();
            return this;
        }

        public Builder withLanguage(String language){
            this.language=language.toString();
            return this;
        }

        public Builder withImage(String image){
            this.image = image;
            return this;
        }

        public Builder withPostCode(Integer postCode){
            this.postCode=postCode;
            return this;
        }

        public Builder withCity(String city){
            this.city=city;
            return this;
        }

        public Builder withRegion(String region){
            this.region = region;
            return this;
        }

        public Builder withStreet(String street){
            this.street = street;
            return this;
        }

        public Builder withStreetNumber(String streetNumber){
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder withByography(String byography){
            this.byography = byography;
            return this;
        }

        public Builder withStudyGrade(String studyGrade){
            this.studyGrade = studyGrade;
            return this;
        }

        public Builder withIssuedDate(ZonedDateTime issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        public Builder withExpirationDate(ZonedDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withRefreshCount(int refreshCount) {
            this.refreshCount = refreshCount;
            return this;
        }

        public Builder withRefreshLimit(int refreshLimit) {
            this.refreshLimit = refreshLimit;
            return this;
        }

        public AuthenticationTokenDetails build() {
            return new AuthenticationTokenDetails(id, username, authorities, name, surname, birtday, language, image, postCode, city, region, street, streetNumber, byography, studyGrade, issuedDate, expirationDate, refreshCount, refreshLimit);
        }
    }
}
