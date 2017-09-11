package mg.etech.mobile.etechapp.contrainte.factory.dto.employe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeDtoFromDOFactoryImpl extends BaseFactory<Employe, EmployeDto> implements EmployeDtoFromDOFactory {

    @Bean(PoleDtoFromDOFactoryImpl.class)
    PoleDtoFromDOFactory poleDtoFromDOFactory;

    @Override
    public EmployeDto getInstance(Employe dObj) {
        EmployeDto employeDto = super.getInstance(dObj);
        employeDto.setPole(poleDtoFromDOFactory.getInstance(dObj.getPole()));
        return employeDto;
    }
}
