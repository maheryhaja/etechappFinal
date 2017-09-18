package mg.etech.mobile.etechapp.contrainte.factory.dto.employe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeDtoFromDOFactoryImpl extends BaseFactory<Employe, EmployeDto> implements EmployeDtoFromDOFactory {

    @Bean(PoleDtoFromDOFactoryImpl.class)
    PoleDtoFromDOFactory poleDtoFromDOFactory;

    @Override
    public EmployeDto getInstance(Employe employe) {
        EmployeDto employeDto = super.getInstance(employe);
        employeDto.setPole(poleDtoFromDOFactory.getInstance(employe.getPole()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            employeDto.setPostes((List<HistoryPosteDto>) objectMapper.readValue(employe.getPostes(), new TypeReference<List<HistoryPosteDto>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeDto;
    }
}
