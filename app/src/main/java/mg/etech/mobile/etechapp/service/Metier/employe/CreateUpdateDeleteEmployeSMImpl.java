package mg.etech.mobile.etechapp.service.Metier.employe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.repository.employe.EmployeRepositoryImpl;
import mg.etech.mobile.etechapp.repository.employe.EmployeRepository;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CreateUpdateDeleteEmployeSMImpl implements CreateUpdateDeleteEmployeSM {

    @Bean(EmployeRepositoryImpl.class)
    EmployeRepository employeRepository;

    @Override
    public void create(Employe employe) {
        employeRepository.insert(employe);
    }

    @Override
    public void deleteAll() {
        employeRepository.deleteAll();
    }

    @Override
    public void create(List<Employe> employes) {
        for (Employe employe : employes) {
            create(employe);
        }
    }

    @Override
    public void deleteById(Long id) {
        employeRepository.delete(id);
    }

    @Override
    public void update(Employe employe) {
        employeRepository.update(employe);
    }
}
