package mg.etech.mobile.etechapp.contrainte.factory.dto;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeDtoFromWsDtoFactoryImpl implements EmployeDtoFromWsDtoFactory {
    @Override
    public EmployeDto getInstanceWithPoleDto(EmployeWsDto employeWsDto, PoleDto poleDto) {

        // a utiliser imperativement pour passer du WS au dto
        EmployeDto employeDto = new EmployeDto();
        employeDto.setId(employeWsDto.getId());
        employeDto.setFirstName(employeWsDto.getFirstName());
        employeDto.setLastName(employeWsDto.getLastName());
        employeDto.setAlias(employeWsDto.getAlias());
        employeDto.setBirthDate(employeWsDto.getBirthDate());
        employeDto.setHiringDate(employeWsDto.getHiringDate());
        employeDto.setMail(employeWsDto.getMail());
        employeDto.setMatricule(employeWsDto.getMatricule());
        employeDto.setMale(employeWsDto.isMale());
        employeDto.setPhoto(employeWsDto.getPhoto());
        employeDto.setPole(poleDto);
        return employeDto;
    }
}
