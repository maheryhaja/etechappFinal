package mg.etech.mobile.etechapp.service.applicatif.synchro.database;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 * un service applicatif qui fournit des observable permettant d'ecouter les changements (CRUD) dans la base de donnees
 */

public interface DataBaseSynchroSA {
    ReplaySubject<EmployeDto> getInitialEmployeList();

    void initialize();

    Observable<EmployeDto> getActualEmployeListObservable();

    List<EmployeDto> getActualList();

    void addEmploye(EmployeDto employeDto);

    void deleteEmploye(EmployeDto employeDto);

    void updateEmploye(EmployeDto data, EmployeDto target);

    void notifyForDelete(EmployeDto employeDto);

    void notifyForUpdate(EmployeDto data, EmployeDto target);

    void notifyForCreate(EmployeDto employeDto);

    Observable<EmployeDto> onAddObservable();

    Observable<EmployeDto> onDeleteObservable();

    Observable<EmployeDto> onUpdateObservable();


}
