package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;

/**
 * Created by mahery.haja on 20/09/2017.
 */

public interface OperationCommand {

    void execute() throws IOException, ApiCallException;

    void onSuccess();
    Long getId();

}
