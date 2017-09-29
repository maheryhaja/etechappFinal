package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
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

        Log.d("mahery-haja", "employe wsdto factory " + (employeWsDtoFromDtoFactory == null));

        //a recuperer from bdl
        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);
        employeBdl.update(employeWsDto);
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
