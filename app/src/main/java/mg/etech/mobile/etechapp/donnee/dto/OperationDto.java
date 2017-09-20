package mg.etech.mobile.etechapp.donnee.dto;

/**
 * Created by mahery.haja on 20/09/2017.
 */

public class OperationDto<Entity> {
    private long id;
    private Entity target;
    private Entity data;
    private String operationName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Entity getData() {
        return data;
    }

    public void setData(Entity data) {
        this.data = data;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
