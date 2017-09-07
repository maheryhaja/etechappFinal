package mg.etech.mobile.etechapp.donnee.wsdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeWsDto {

    public static final String DATE_WS_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date birthDate;

    @JsonProperty("hiringDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WS_PATTERN)
    private Date hiringDate;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("matricule")
    private Long matricule;

    @JsonProperty("pole")
    private Long pole;

    @JsonProperty("male")
    boolean male;

    @JsonProperty("photo")
    private String photo;
}
