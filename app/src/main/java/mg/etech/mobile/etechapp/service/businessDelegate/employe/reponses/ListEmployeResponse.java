package mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListEmployeResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("datas")
    private List<EmployeWsDto> datas;

    @JsonProperty("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public List<EmployeWsDto> getDatas() {
        return datas;
    }

    public String getMessage() {
        return message;
    }
}
