package mg.etech.mobile.etechapp.donnee.dto;

/**
 * Created by mahery.haja on 11/09/2017.
 */
public class PosteDto {
    private Long id;
    private String name;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
