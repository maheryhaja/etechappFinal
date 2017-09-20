package mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSM;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSMImpl;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.CreateEmployeCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.OperationCommand;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationCommandFactoryImpl implements OperationCommandFactory {

    @Bean(OperationSMImpl.class)
    OperationSM operationSM;

    @Bean(OperationFromDtoFactoryImpl.class)
    OperationFromDtoFactory operationFromDtoFactory;

    @Override
    public OperationCommand create(String operationType, EmployeDto employeDtoData, EmployeDto target) {

        OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<>();
        employeDtoOperationDto.setOperationName(operationType);
        employeDtoOperationDto.setData(employeDtoData);
        employeDtoOperationDto.setTarget(target);

        Operation op = operationFromDtoFactory.getInstance(employeDtoOperationDto);

        Log.d("mahery-haja", "creation de op " + op.getData());
        operationSM.create(op);

        if (op.getId() != 0) {
            employeDtoOperationDto.setId(op.getId());


            // reagard type of command
            if (operationType.equals(OperationType.CREATE)) {
                CreateEmployeCommand command = new CreateEmployeCommand();
                command.setOperation(employeDtoOperationDto);
                Log.d("mahery-haja", "create command successfull");
                return command;
            }

            // throw exeption in case of not implemented
        }

        Log.d("mahery-haja", "creation failed");

        return null;
    }
}
