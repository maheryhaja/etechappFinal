package mg.etech.mobile.etechapp.donnee.wsdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeWsDto {

    public static final String DATE_WS_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date birthDate;

    @JsonProperty("hiringDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date hiringDate;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("matricule")
    private Long matricule;

    @JsonProperty("pole")
    private Long pole;

    @JsonProperty("male")
    boolean male;

    @JsonProperty("photo")
    private String photo;


    public static String getDateWsPattern() {
        return DATE_WS_PATTERN;
    }

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

    public Long getPole() {
        return pole;
    }

    public void setPole(Long pole) {
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
}
