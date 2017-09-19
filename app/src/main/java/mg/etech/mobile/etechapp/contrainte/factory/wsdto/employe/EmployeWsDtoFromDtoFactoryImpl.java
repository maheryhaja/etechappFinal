package mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 15/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeWsDtoFromDtoFactoryImpl extends BaseFactory<EmployeDto, EmployeWsDto> implements EmployeWsDtoFromDtoFactory {

    @Bean(HistoryPosteWsDtoFromDtoFactoryImpl.class)
    HistoryPosteWsDtoFromDtoFactory historyPosteWsDtoFromDtoFactory;

    @Override
    public EmployeWsDto getInstance(EmployeDto employeDto) {

        Log.d("mahery-haja", "transformation begin");
        EmployeWsDto employeWsDto = new EmployeWsDto();
        employeWsDto.setLastName(employeDto.getLastName());
        employeWsDto.setFirstName(employeDto.getFirstName());
        employeWsDto.setAlias(employeDto.getAlias());
        employeWsDto.setMatricule(employeDto.getMatricule());
        employeWsDto.setMail(employeDto.getMail());
        employeWsDto.setMale(employeDto.isMale());
        employeWsDto.setPhoto(employeDto.getPhoto());
        employeWsDto.setBirthDate(employeDto.getBirthDate());
        employeWsDto.setHiringDate(employeDto.getHiringDate());
        employeWsDto.setPostes(historyPosteWsDtoFromDtoFactory.getInstance(employeDto.getPostes()));
        employeWsDto.setPole(employeDto.getPole().getId());
        Log.d("mahery-haja", "transformation " + employeWsDto.getPole());
        return employeWsDto;
    }
}
