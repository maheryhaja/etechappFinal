package mg.etech.mobile.etechapp.service.Metier.poste;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import io.reactivex.Single;
import mg.etech.mobile.etechapp.donnee.domainobject.Poste;
import mg.etech.mobile.etechapp.repository.poste.PosteRepository;
import mg.etech.mobile.etechapp.repository.poste.PosteRepositoryImpl;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrievePosteSMImpl implements RetrievePosteSM {

    @Bean(PosteRepositoryImpl.class)
    PosteRepository posteRepository;

    @Override
    public List<Poste> findAll() {
        return posteRepository.findAll();
    }
}
