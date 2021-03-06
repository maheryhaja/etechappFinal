package mg.etech.mobile.etechapp.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by mahery.haja on 13/09/2017.
 */
//il est necessaire d'utiliser les annotation jackson afin de sauvegarder l'historique des postes en tant que string
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryPosteDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("datePromotion")
    private Date datePromotion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    @Override
    public boolean equals(Object o) {
        //verification de reference
        if (this == o) return true;

        //verfication de classe
        if (o == null || getClass() != o.getClass()) return false;

        HistoryPosteDto that = (HistoryPosteDto) o;

        //verification de Id
        //if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;

        //verification de name
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;

        return getDatePromotion() != null ? getDatePromotion().equals(that.getDatePromotion()) : that.getDatePromotion() == null;

    }

}
