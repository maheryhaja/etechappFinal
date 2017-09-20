package mg.etech.mobile.etechapp.donnee.domainobject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mahery.haja on 20/09/2017.
 */
// classe utilise pour serialiser les operation hors ligne
@DatabaseTable(tableName = "operation")
public class Operation {

    @DatabaseField(generatedId = true, columnName = "id")
    private long id;

    @DatabaseField(columnName = "operationName")
    private String OperationName;

    @DatabaseField(columnName = "target")
    private String target;

    @DatabaseField(columnName = "data")
    private String data;

    @DatabaseField(columnName = "className")
    private String className;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
