package mg.etech.mobile.etechapp.contrainte.factory.dto.employe;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeDtoFromWsDtoFactory {
    EmployeDto getInstanceWithPoleDto(EmployeWsDto employeWsDto, PoleDto poleDto);
}
