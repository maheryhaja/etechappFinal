package mg.etech.mobile.etechapp.service.Metier.employe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.repository.employe.EmployeRepository;
import mg.etech.mobile.etechapp.repository.employe.EmployeRepositoryImpl;
import mg.etech.mobile.etechapp.repository.pole.PoleRepository;
import mg.etech.mobile.etechapp.repository.pole.PoleRepositoryImpl;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrieveEmployeSMImpl implements RetrieveEmployeSM {

    @Bean(EmployeRepositoryImpl.class)
    EmployeRepository employeRepository;

    @Bean(PoleRepositoryImpl.class)
    PoleRepository poleRepository;

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }


    //assume that pole is from database !!!!    IMPORTANT
    @Override
    public List<Employe> findByPole(Pole pole) {
        return new ArrayList<Employe>(pole.getEmployes());
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id);
    }
}
