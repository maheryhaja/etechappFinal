package mg.etech.mobile.etechapp.donnee.dto;


import java.util.Date;
import java.util.List;

/**
 * Created by mahery.haja on 06/09/2017.
 */
public class EmployeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String alias;
    private Date birthDate;
    private Date hiringDate;
    private String mail;
    private Long matricule;
    private PoleDto pole;
    boolean male;
    private String photo;
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
