package mg.etech.mobile.etechapp.service.businessDelegate.poste.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.wsdto.PosteWsDto;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListePosteReponse {

    @JsonProperty("postes")
    private List<PosteWsDto> postes;

    public List<PosteWsDto> getPostes() {
        return postes;
    }
}
