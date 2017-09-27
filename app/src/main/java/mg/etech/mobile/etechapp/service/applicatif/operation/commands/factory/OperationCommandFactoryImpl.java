package mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation.OperationFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSM;
import mg.etech.mobile.etechapp.service.Metier.operation.OperationSMImpl;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.CreateEmployeCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.DeleteEmployeCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.OperationCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.UpdateEmployeCommand;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationCommandFactoryImpl implements OperationCommandFactory {

    @Bean(OperationSMImpl.class)
    OperationSM operationSM;

    @Bean(OperationFromDtoFactoryImpl.class)
    OperationFromDtoFactory operationFromDtoFactory;

    @RootContext
    Context context;

    //pour les employes
    @Override
    public OperationCommand createFromEmployeDto(String operationType, EmployeDto employeDtoData, EmployeDto target) {


        OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<>();
        employeDtoOperationDto.setOperationName(operationType);
        employeDtoOperationDto.setData(employeDtoData);
        employeDtoOperationDto.setTarget(target);

            // reagard type of command
            if (operationType.equals(OperationType.CREATE)) {
                CreateEmployeCommand command = new CreateEmployeCommand(context);
                command.setOperation(employeDtoOperationDto);
                Log.d("mahery-haja", "create command successfull");
                return command;
            }

        if (operationType.equals(OperationType.UPDATE)) {
            UpdateEmployeCommand command = new UpdateEmployeCommand(context);
            command.setOperation(employeDtoOperationDto);
            return command;
        }

        if (operationType.equals(OperationType.DELETE)) {
            DeleteEmployeCommand command = new DeleteEmployeCommand(context);
            command.setOperation(employeDtoOperationDto);
            return command;
        }


        Log.d("mahery-haja", "creation failed");

        return null;
    }

    @Override
    public OperationCommand create(OperationDto operationDto) {

        if (operationDto.getClassName().contains(EmployeDto.class.getName())) {
            EmployeDto source = null;
            EmployeDto target = null;

            if (operationDto.getData() != null) {
                source = (EmployeDto) operationDto.getData();
            }

            if (operationDto.getTarget() != null) {
                target = (EmployeDto) operationDto.getTarget();
            }

            return createFromEmployeDto(operationDto.getOperationName(), source, target);

        }
        return null;
    }
}
