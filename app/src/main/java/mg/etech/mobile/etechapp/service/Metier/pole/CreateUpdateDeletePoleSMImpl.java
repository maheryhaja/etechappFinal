package mg.etech.mobile.etechapp.service.Metier.pole;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.repository.pole.PoleRepository;
import mg.etech.mobile.etechapp.repository.pole.PoleRepositoryImpl;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CreateUpdateDeletePoleSMImpl implements CreateUpdateDeletePoleSM {

    @Bean(PoleRepositoryImpl.class)
    PoleRepository poleRepository;

    @Override
    public void create(Pole pole) {
        poleRepository.insert(pole);
    }

    @Override
    public void deleteAll() {
        poleRepository.deleteAll();
    }
}
