package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;

/**
 * Created by mahery.haja on 27/09/2017.
 */

public class DeleteEmployeCommand extends BaseEmployeCommand implements OperationCommand {
    public DeleteEmployeCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        // execute delete and notify success
        employeBdl.delete(employeWsDtoFromDtoFactory.getInstance(employeDtoOperationDto.getData()));
    }

    @Override
    public Long getId() {
        return employeDtoOperationDto.getId();
    }

    @Override
    public void onSuccess() {
        employeSA.deleteById(employeDtoOperationDto.getData().getId());
        dataBaseSynchroSA.notifyForDelete(employeDtoOperationDto.getData());
        operationStackSynchroSA.notifySuccess(employeDtoOperationDto);
    }
}
