package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 20/09/2017.
 */
public class CreateEmployeCommand extends BaseEmployeCommand implements OperationCommand {

    public CreateEmployeCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        // execute create and notify success

        Log.d("mahery-haja", "process Operation id " + employeDtoOperationDto.getId());
        EmployeDto data = employeDtoOperationDto.getData();

        Log.d("mahery-haja", "employe wsdto factory " + (employeWsDtoFromDtoFactory == null));

        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);


        EmployeDto created = employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeBdl.create(employeWsDto), data.getPole());

        employeDtoOperationDto.setData(created);
    }

    @Override
    public void onSuccess() {
        employeSA.create(employeDtoOperationDto.getData());
        dataBaseSynchroSA.notifyForCreate(employeDtoOperationDto.getData());
        operationStackSynchroSA.notifySuccess(employeDtoOperationDto);

    }

    @Override
    public Long getId() {
        return employeDtoOperationDto.getId();
    }

}
