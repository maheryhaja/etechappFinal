package mg.etech.mobile.etechapp.service.Metier.operation;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Operation;

/**
 * Created by mahery.haja on 20/09/2017.
 */

public interface OperationSM {
    List<Operation> findAll();

    Operation findById(Long idOperation);

    void create(Operation operation);

    Long createWithIdReturned(Operation operation);

    void deleteAll();

    void deleteById(Long idOperation);

    void update(Operation operation);


}
