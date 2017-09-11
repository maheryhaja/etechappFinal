package mg.etech.mobile.etechapp.service.Metier.poste;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Poste;
import mg.etech.mobile.etechapp.repository.poste.PosteRepository;
import mg.etech.mobile.etechapp.repository.poste.PosteRepositoryImpl;

/**
 * Created by maheryHaja on 9/11/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CreateUpdateDeletePosteSMImpl implements CreateUpdateDeletePosteSM {

    @Bean(PosteRepositoryImpl.class)
    PosteRepository posteRepository;

    @Override
    public void create(Poste poste) {
        posteRepository.insert(poste);
    }

    @Override
    public void deleteAll() {
        posteRepository.deleteAll();
    }

    @Override
    public void create(List<Poste> postes) {
       posteRepository.insertBatch(postes);
    }
}
