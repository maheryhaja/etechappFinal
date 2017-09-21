package mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;

/**
 * Created by mahery.haja on 21/09/2017.
 * service applicatif qui fournit des observables permettant d'ecouter les changements CUD au niveau des operations
 */

public interface OperationStackSynchroSA {
    ReplaySubject<OperationDto> getInitialOperationList();

    Observable<OperationDto<EmployeDto>> getInitialOperationListForEmploye();

    void addOperation(OperationDto operationDto);

    void initialize();
}
