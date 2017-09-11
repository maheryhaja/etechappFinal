package mg.etech.mobile.etechapp.service.applicatif.poste;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.contrainte.factory.domainobject.poste.PosteFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.poste.PosteFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;
import mg.etech.mobile.etechapp.service.Metier.poste.CreateUpdateDeletePosteSM;
import mg.etech.mobile.etechapp.service.Metier.poste.CreateUpdateDeletePosteSMImpl;
import mg.etech.mobile.etechapp.service.Metier.poste.RetrievePosteSM;
import mg.etech.mobile.etechapp.service.Metier.poste.RetrievePosteSMImpl;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PosteSAImpl implements PosteSA {

    @Bean(RetrievePosteSMImpl.class)
    RetrievePosteSM retrievePosteSM;

    @Bean(PosteDtoFromDOFactoryImpl.class)
    PosteDtoFromDOFactory posteDtoFromDOFactory;

    @Bean(CreateUpdateDeletePosteSMImpl.class)
    CreateUpdateDeletePosteSM createUpdateDeletePosteSM;

    @Bean(PosteFromDtoFactoryImpl.class)
    PosteFromDtoFactory posteFromDtoFactory;

    @Override
    public List<PosteDto> findAll() {
        return posteDtoFromDOFactory.getInstance(retrievePosteSM.findAll());
    }

    @Override
    public void create(PosteDto posteDto) {
        createUpdateDeletePosteSM.create(posteFromDtoFactory.getInstance(posteDto));
    }

    @Override
    public void create(List<PosteDto> posteDtos) {
        createUpdateDeletePosteSM.create(posteFromDtoFactory.getInstance(posteDtos));
    }

    @Override
    public void deleteAll() {
        createUpdateDeletePosteSM.deleteAll();
    }
}
