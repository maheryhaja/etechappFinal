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

    void updateOperation(OperationDto operationDto);

    void deleteOperation(OperationDto operationDto);


    void deleteOperationById(int positiveId);

    Observable<OperationDto> getActualList();

    Observable<OperationDto> onAddObservable();

    Observable<OperationDto> onDeleteObservable();


    void notifySuccess(OperationDto operationDto);

    void notifyError(OperationDto operationDto);


    void initialize();

    Observable<OperationDto> onUpdateObservable();

    Observable<OperationDto> onSucceedObservable();
}
