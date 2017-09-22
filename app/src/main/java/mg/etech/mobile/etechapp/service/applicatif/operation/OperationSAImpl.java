package mg.etech.mobile.etechapp.service.applicatif.operation;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.operation.OperationDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.operation.OperationDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSM;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSMImpl;

/**
 * Created by mahery.haja on 21/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationSAImpl implements OperationSA {

    @Bean(OperationFromDtoFactoryImpl.class)
    OperationFromDtoFactory operationFromDtoFactory;

    @Bean(OperationDtoFromDOFactoryImpl.class)
    OperationDtoFromDOFactory operationDtoFromDOFactory;

    @Bean(OperationSMImpl.class)
    OperationSM operationSM;


    @Override
    public void create(OperationDto operationDto) {
        Long idCreated = operationSM.create(operationFromDtoFactory.getInstance(operationDto));
        operationDto.setId(idCreated);
    }

    @Override
    public List<OperationDto> findAll() {
        List<OperationDto> operationDtos = new ArrayList<>();
        List<Operation> operations = operationSM.findAll();

        for (Operation operation : operations) {
            operationDtos.add(operationDtoFromDOFactory.getInstance(operation));
            Log.d("mahery-haja", "operation size " + operationDtoFromDOFactory.getInstance(operation).getOperationName());
        }
        return operationDtos;
    }

    @Override
    public void deleteById(Long id) {
        operationSM.deleteById(id);
    }
}
