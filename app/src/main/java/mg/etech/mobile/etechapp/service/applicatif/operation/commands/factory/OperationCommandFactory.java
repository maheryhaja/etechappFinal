package mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.OperationCommand;

/**
 * Created by mahery.haja on 20/09/2017.
 */

public interface OperationCommandFactory {
    OperationCommand createFromEmployeDto(String operationType, EmployeDto employeDtoData, EmployeDto target, Long id);

    OperationCommand create(OperationDto operationDto);
}
