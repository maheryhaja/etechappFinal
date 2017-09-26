package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;

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

    }

    @Override
    public Long getId() {
        return null;
    }
}
