package mg.etech.mobile.etechapp.service.applicatif.employe;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.contrainte.factory.domainobject.employe.EmployeFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.employe.EmployeFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.service.Metier.employe.CreateUpdateDeleteEmployeSM;
import mg.etech.mobile.etechapp.service.Metier.employe.CreateUpdateDeleteEmployeSMImpl;
import mg.etech.mobile.etechapp.service.Metier.employe.RetrieveEmployeSM;
import mg.etech.mobile.etechapp.service.Metier.employe.RetrieveEmployeSMImpl;
import mg.etech.mobile.etechapp.service.Metier.pole.RetrievePoleSM;
import mg.etech.mobile.etechapp.service.Metier.pole.RetrievePoleSMImpl;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeSAImpl implements EmployeSA {

    @Bean(CreateUpdateDeleteEmployeSMImpl.class)
    CreateUpdateDeleteEmployeSM createUpdateDeleteEmployeSM;

    @Bean(RetrieveEmployeSMImpl.class)
    RetrieveEmployeSM retrieveEmployeSM;


    @Bean(RetrievePoleSMImpl.class)
    RetrievePoleSM retrievePoleSM;


    @Bean(EmployeDtoFromDOFactoryImpl.class)
    EmployeDtoFromDOFactory employeDtoFromDOFactory;

    @Bean(EmployeFromDtoFactoryImpl.class)
    EmployeFromDtoFactory employeFromDtoFactory;


    @Override
    public List<EmployeDto> findAll() {
        return employeDtoFromDOFactory.getInstance(retrieveEmployeSM.findAll());
    }

    @Override
    public void create(EmployeDto employeDto) {
        createUpdateDeleteEmployeSM.create(employeFromDtoFactory.getInstance(employeDto));
    }

    @Override
    public void create(List<EmployeDto> employeDtos) {
        Log.d("mahery-haja", "create list");
        List<Employe> employes = employeFromDtoFactory.getInstance(employeDtos);
        for (Employe e : employes)
            Log.d("mahery-haja", e.getPole().getName() + " " + e.getFirstName());
        createUpdateDeleteEmployeSM.create(employes);
    }

    @Override
    public void deleteAll() {
        createUpdateDeleteEmployeSM.deleteAll();
    }

    @Override
    public List<EmployeDto> findByPole(PoleDto poleDto) {

        Pole pole = retrievePoleSM.findById(poleDto.getId());

        return employeDtoFromDOFactory.getInstance(retrieveEmployeSM.findByPole(pole));
    }

    @Override
    public EmployeDto findById(Long id) {
        return employeDtoFromDOFactory.getInstance(retrieveEmployeSM.findById(id));
    }
}
