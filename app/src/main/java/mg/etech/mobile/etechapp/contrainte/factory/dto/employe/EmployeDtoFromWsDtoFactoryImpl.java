package mg.etech.mobile.etechapp.contrainte.factory.dto.employe;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.HistoryPosteDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.HistoryPosteDtoFromWsDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeDtoFromWsDtoFactoryImpl implements EmployeDtoFromWsDtoFactory {

    @Bean(HistoryPosteDtoFromWsDtoFactoryImpl.class)
    HistoryPosteDtoFromWsDtoFactory factory;

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
        employeDto.setEncodedPhoto(employeWsDto.getEncodedPhoto());

        //set pole
        if (poleDto != null)
        employeDto.setPole(poleDto);

        Log.d("mahery-haja", "appel conversion history");
        if (employeWsDto.getPostes() != null)
        employeDto.setPostes(factory.getInstance(employeWsDto.getPostes()));
        return employeDto;
    }
}
