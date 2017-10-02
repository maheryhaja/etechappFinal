package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class UpdateEmployeCommand extends BaseEmployeCommand implements OperationCommand {


    public UpdateEmployeCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        // suppose that there are no conflicts
        // execute update and notify for success

        EmployeDto data = employeDtoOperationDto.getData();


        //a recuperer from bdl
        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);
        EmployeWsDto employeWsDtoUpdated = employeBdl.update(employeWsDto);

        PoleDto poleDto = poleSA.findPoleById(employeWsDtoUpdated.getPole());

        employeDtoOperationDto.setData(employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDtoUpdated, poleDto));


    }

    @Override
    public Long getId() {

        return employeDtoOperationDto.getId();
    }

    @Override
    public void onSuccess() {
        employeSA.update(employeDtoOperationDto.getData());
        dataBaseSynchroSA.notifyForUpdate(employeDtoOperationDto.getData(), employeDtoOperationDto.getTarget());
        operationStackSynchroSA.notifySuccess(employeDtoOperationDto);
    }


}
