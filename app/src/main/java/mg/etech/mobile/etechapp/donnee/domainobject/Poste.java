package mg.etech.mobile.etechapp.donnee.domainobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@DatabaseTable(tableName = "poste")
public class Poste {
    @DatabaseField(id = true, columnName = "id")
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "tag")
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
