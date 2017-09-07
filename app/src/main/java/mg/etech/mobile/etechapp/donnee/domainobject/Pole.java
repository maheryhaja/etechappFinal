package mg.etech.mobile.etechapp.donnee.domainobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@DatabaseTable(tableName = "pole")
public class Pole {

    @DatabaseField(columnName = "id", generatedId = true, unique = true)
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "idServer")
    private String idServer;

    @ForeignCollectionField(eager = true)
    private Collection<Employe> employes;

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
}
