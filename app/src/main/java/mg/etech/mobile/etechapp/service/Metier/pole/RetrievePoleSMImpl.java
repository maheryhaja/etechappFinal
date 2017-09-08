package mg.etech.mobile.etechapp.service.Metier.pole;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.repository.pole.PoleRepository;
import mg.etech.mobile.etechapp.repository.pole.PoleRepositoryImpl;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrievePoleSMImpl implements RetrievePoleSM {

    @Bean(PoleRepositoryImpl.class)
    PoleRepository poleRepository;

    @Override
    public List<Pole> findAll() {
        return poleRepository.findAll();
    }

    @Override
    public Pole findById(Long id) {
        return poleRepository.findById(id);
    }
}
