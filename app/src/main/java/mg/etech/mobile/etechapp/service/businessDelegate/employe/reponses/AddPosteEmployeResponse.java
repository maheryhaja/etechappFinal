package mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import mg.etech.mobile.etechapp.donnee.wsdto.HistoryPosteWsDto;

/**
 * Created by mahery.haja on 03/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPosteEmployeResponse {


    @JsonProperty("success")
    private boolean success;

    @JsonProperty("poste")
    private HistoryPosteWsDto poste;

    @JsonProperty("message")
    private String message;

    public HistoryPosteWsDto getPoste() {
        return poste;
    }

    public void setPoste(HistoryPosteWsDto poste) {
        this.poste = poste;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
