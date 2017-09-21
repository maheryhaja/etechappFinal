package mg.etech.mobile.etechapp.service.applicatif.synchro.database;

import io.reactivex.subjects.ReplaySubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 * un service applicatif qui fournit des observable permettant d'ecouter les changements (CRUD) dans la base de donnees
 */

public interface DataBaseSynchroSA {
    ReplaySubject<EmployeDto> getInitialEmployeList();

    void initialize();
}
