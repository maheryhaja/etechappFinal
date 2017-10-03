package mg.etech.mobile.etechapp.donnee.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    @JsonProperty("encodedPhoto")
    private String encodedPhoto;

    @JsonProperty("postes")
    private List<HistoryPosteDto> postes = new ArrayList<>();

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


    @Override
    public boolean equals(Object o) {


        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmployeDto that = (EmployeDto) o;


        if (equalsWithoutPoste(o)) {

            // for postes not sure ---> a verifier
            if (getPostes() != null ? !(getPostes().size() == that.getPostes().size()) : that.getPostes() != null) {
                return false;
            } else {
                boolean posteEquals = true;

                for (int i = 0; i < getPostes().size() && posteEquals; i++) {
                    posteEquals = (getPostes().get(i).equals(that.getPostes().get(i)));
                }
                return posteEquals;
            }
        } else {
            return false;
        }
    }

    public boolean equalsWithoutPoste(Object o) {


        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmployeDto that = (EmployeDto) o;

        // for is Male ok
        if (isMale() != that.isMale()) return false;

        // for id ok
        if (!getId().equals(that.getId())) return false;

        //for first Name ok

        if (getLastName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;

        // for Last Name ok
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;


        // for allias ok
        if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null)
            return false;

        // for birthDate ok
        if (getBirthDate() != null ? !getBirthDate().equals(that.getBirthDate()) : that.getBirthDate() != null)
            return false;

        // for hiring date okey
        if (getHiringDate() != null ? !getHiringDate().equals(that.getHiringDate()) : that.getHiringDate() != null)
            return false;

        // for mail
        if (getMail() != null ? !getMail().equals(that.getMail()) : that.getMail() != null)
            return false;

        // for matricule ok
        if (getMatricule() != null ? !getMatricule().equals(that.getMatricule()) : that.getMatricule() != null)
            return false;


        // for pole not ok
        if (getPole() != null ? !getPole().equals(that.getPole()) : that.getPole() != null)
            return false;


        // for photo ok
        if (getPhoto() != null ? !getPhoto().equals(that.getPhoto()) : that.getPhoto() != null)
            return false;
        if (getEncodedPhoto() != null ? !getEncodedPhoto().equals(that.getPhoto()) : that.getEncodedPhoto() != null)
            return false;

        return true;
    }

    public String getEncodedPhoto() {
        return encodedPhoto;
    }

    public void setEncodedPhoto(String encodedPhoto) {
        this.encodedPhoto = encodedPhoto;
    }
}
