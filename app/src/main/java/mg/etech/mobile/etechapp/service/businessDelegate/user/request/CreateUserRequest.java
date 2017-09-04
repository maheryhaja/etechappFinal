package mg.etech.mobile.etechapp.service.businessDelegate.user.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mahery.haja on 01/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    @JsonProperty("firstName")
    private String firstname;

    @JsonProperty("lastName")
    private String lastname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("login")
    private String login;

    @JsonProperty("encodedPhoto")
    private String encodedPhoto;


    public CreateUserRequest(String firstname, String lastname, String password, String login, String encodedPhoto) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.login = login;
        this.encodedPhoto = encodedPhoto;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEncodedPhoto() {
        return encodedPhoto;
    }

    public void setEncodedPhoto(String encodedPhoto) {
        this.encodedPhoto = encodedPhoto;
    }
}
