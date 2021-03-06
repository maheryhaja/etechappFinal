package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.donnee.wsdto.HistoryPosteWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeBdl {
    List<EmployeWsDto> findAll() throws IOException, ApiCallException;

    EmployeWsDto create(EmployeWsDto employeWsDto) throws IOException, ApiCallException;

    EmployeWsDto update(EmployeWsDto employeWsDto) throws IOException, ApiCallException;

    HistoryPosteWsDto addPoste(HistoryPosteWsDto historyPosteWsDto, Long idEmploye) throws IOException, ApiCallException;

    void delete(EmployeWsDto employeWsDto) throws IOException, ApiCallException;
}
