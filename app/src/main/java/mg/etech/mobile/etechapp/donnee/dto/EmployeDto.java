package mg.etech.mobile.etechapp.donnee.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("allias")
    private String alias;

    @JsonProperty("birthDate")
    private Date birthDate;

    @JsonProperty("hiringDate")
    private Date hiringDate;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("matricule")
    private Long matricule;

    @JsonProperty("pole")
    private PoleDto pole;

    @JsonProperty("male")
    boolean male;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("postes")
    private List<HistoryPosteDto> postes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getMatricule() {
        return matricule;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public PoleDto getPole() {
        return pole;
    }

    public void setPole(PoleDto pole) {
        this.pole = pole;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<HistoryPosteDto> getPostes() {
        return postes;
    }

    public void setPostes(List<HistoryPosteDto> postes) {
        this.postes = postes;
    }
}
