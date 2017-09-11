package mg.etech.mobile.etechapp.contrainte.factory.domainobject.employe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.pole.PoleFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.pole.PoleFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeFromDtoFactoryImpl extends BaseFactory<EmployeDto, Employe> implements EmployeFromDtoFactory {


    @Bean(PoleFromDtoFactoryImpl.class)
    PoleFromDtoFactory poleFromDtoFactory;

    @Override
    public Employe getInstance(EmployeDto dObj) {
        Employe employe = super.getInstance(dObj);
        employe.setPole(poleFromDtoFactory.getInstance(dObj.getPole()));
        return employe;
    }
}
