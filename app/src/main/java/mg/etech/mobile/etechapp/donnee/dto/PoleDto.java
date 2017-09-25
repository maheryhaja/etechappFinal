package mg.etech.mobile.etechapp.donnee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("idServer")
    private String idServer;

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

    public String getIdServer() {
        return idServer;
    }

    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoleDto poleDto = (PoleDto) o;

        if (getId() != null ? !getId().equals(poleDto.getId()) : poleDto.getId() != null)
            return false;
        if (getName() != null ? !getName().equals(poleDto.getName()) : poleDto.getName() != null)
            return false;
        return getIdServer() != null ? getIdServer().equals(poleDto.getIdServer()) : poleDto.getIdServer() == null;

    }

}
