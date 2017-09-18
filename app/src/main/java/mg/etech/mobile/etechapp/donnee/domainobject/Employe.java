package mg.etech.mobile.etechapp.donnee.domainobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@DatabaseTable(tableName = "Employe")
public class Employe {

    @DatabaseField(unique = true, columnName = "id", id = true)
    private Long id;

    @DatabaseField(columnName = "firstName")
    private String firstName;

    @DatabaseField(columnName = "lastName")
    private String lastName;

    @DatabaseField(columnName = "alias")
    private String alias;

    @DatabaseField(columnName = "birthDate")
    private Date birthDate;

    @DatabaseField(columnName = "hiringDate")
    private Date hiringDate;

    @DatabaseField(columnName = "mail")
    private String mail;

    @DatabaseField(columnName = "matricule")
    private Long matricule;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Pole pole;

    @DatabaseField(columnName = "male")
    boolean male;

    @DatabaseField(columnName = "photo")
    private String photo;

    @DatabaseField(columnName = "postes")
    private String postes;

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

    public Pole getPole() {
        return pole;
    }

    public void setPole(Pole pole) {
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

    public String getPostes() {
        return postes;
    }

    public void setPostes(String postes) {
        this.postes = postes;
    }
}
