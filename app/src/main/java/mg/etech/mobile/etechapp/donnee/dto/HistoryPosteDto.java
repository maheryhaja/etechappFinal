package mg.etech.mobile.etechapp.donnee.dto;

import java.util.Date;

/**
 * Created by mahery.haja on 13/09/2017.
 */

public class HistoryPosteDto {

    private Long id;
    private String name;
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
}
