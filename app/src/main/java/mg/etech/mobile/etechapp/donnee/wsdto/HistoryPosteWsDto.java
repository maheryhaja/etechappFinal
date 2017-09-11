package mg.etech.mobile.etechapp.donnee.wsdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import static mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto.DATE_WS_PATTERN;

/**
 * Created by maheryHaja on 9/11/2017.
 * une classe qui represente l'évolution des postes de l'employé
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryPosteWsDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("datePromotion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date datePromotion;

}
