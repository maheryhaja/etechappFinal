package mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 26/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateEmployeResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("employe")
    private EmployeWsDto employe;

    @JsonProperty("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public EmployeWsDto getEmploye() {
        return employe;
    }

    public String getMessage() {
        return message;
    }
}
