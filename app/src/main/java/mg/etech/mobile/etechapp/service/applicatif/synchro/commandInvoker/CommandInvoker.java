package mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker;

import io.reactivex.Observable;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;

/**
 * Created by mahery.haja on 22/09/2017.
 */

public interface CommandInvoker {
    void initialize();

    void launch();
    void processStack();

    Observable<OperationDto> onProceessingObservable();

    Observable<OperationDto> onErrorObservable();
}
