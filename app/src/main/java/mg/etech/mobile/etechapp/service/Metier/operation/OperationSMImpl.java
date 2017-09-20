package mg.etech.mobile.etechapp.service.Metier.operation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.repository.operation.OperationRepository;
import mg.etech.mobile.etechapp.repository.operation.OperationRepositoryImpl;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationSMImpl implements OperationSM {

    @Bean(OperationRepositoryImpl.class)
    OperationRepository operationRepository;

    @Override
    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    @Override
    public Operation findById(Long idOperation) {
        return operationRepository.findById(idOperation);
    }

    @Override
    public void create(Operation operation) {
        operationRepository.insert(operation);
    }

    @Override
    public void deleteAll() {
        operationRepository.deleteAll();
    }

    @Override
    public void deleteById(Long idOperation) {
        operationRepository.delete(idOperation);
    }

    @Override
    public void update(Operation operation) {
        operationRepository.update(operation);
    }

    @Override
    public Long createWithIdReturned(Operation operation) {
        return Long.valueOf(operationRepository.insertAndReturn(operation));
    }
}
