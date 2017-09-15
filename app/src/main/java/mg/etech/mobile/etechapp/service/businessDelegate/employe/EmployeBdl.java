package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeBdl {
    List<EmployeWsDto> findAll() throws IOException, ApiCallException;

    EmployeWsDto create(EmployeWsDto employeWsDto) throws IOException, ApiCallException;
}
