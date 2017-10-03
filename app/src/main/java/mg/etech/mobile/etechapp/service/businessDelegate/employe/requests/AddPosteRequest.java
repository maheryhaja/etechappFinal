package mg.etech.mobile.etechapp.service.businessDelegate.employe.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import mg.etech.mobile.etechapp.donnee.wsdto.HistoryPosteWsDto;

import static mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto.DATE_WS_PATTERN;

/**
 * Created by mahery.haja on 03/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddPosteRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("datePromotion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date datePromotion;

    @JsonProperty("idEmploye")
    private Long idEmploye;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatePromotion() {
        return datePromotion;
    }

    public void setDatePromotion(Date datePromotion) {
        this.datePromotion = datePromotion;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public AddPosteRequest(String name, Date datePromotion, Long idEmploye) {
        this.name = name;
        this.datePromotion = datePromotion;
        this.idEmploye = idEmploye;
    }

    public static AddPosteRequest fromHistoryPosteDto(HistoryPosteWsDto historyPosteWsDto, Long idEmploye) {

        return new AddPosteRequest(historyPosteWsDto.getName(), historyPosteWsDto.getDatePromotion(), idEmploye);
    }
}
