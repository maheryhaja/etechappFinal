package mg.etech.mobile.etechapp.service.businessDelegate.user.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mahery.haja on 01/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRequest {

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    public LoginUserRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
