package mg.etech.mobile.etechapp.donnee.dto;

import android.util.Log;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;


        if (o == null || getClass() != o.getClass()) return false;

        PosteDto posteDto = (PosteDto) o;


        if (!getId().equals(posteDto.getId())) return false;
        if (getName() != null ? !getName().equals(posteDto.getName()) : posteDto.getName() != null)
            return false;
        return getTag() != null ? getTag().equals(posteDto.getTag()) : posteDto.getTag() == null;

    }

}
