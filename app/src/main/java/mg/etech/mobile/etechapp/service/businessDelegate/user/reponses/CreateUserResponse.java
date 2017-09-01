package mg.etech.mobile.etechapp.service.businessDelegate.user.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("user")
    private UserWsDto user;

    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public UserWsDto getUser() {
        return user;
    }

}
