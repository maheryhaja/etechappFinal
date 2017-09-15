package mg.etech.mobile.etechapp.service.applicatif.synchro.back;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface BackSynchronizerSA {
    public void synch() throws IOException, ApiCallException;

    EmployeDto createEmploye(EmployeDto employeDto) throws IOException, ApiCallException;
}
