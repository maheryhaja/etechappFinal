package mg.etech.mobile.etechapp.service.businessDelegate.poste;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.wsdto.PosteWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.poste.reponses.ListePosteReponse;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PostebdlImpl implements Postebdl {

    @Bean(RetrofitSingletonImpl.class)
    RetrofitSingleton retrofitSingleton;

    private PosteApi posteApi;

    @AfterInject
    void initAfterInject() {
        posteApi = retrofitSingleton.getDefaultForRx().create(PosteApi.class);
    }

    @Override
    public List<PosteWsDto> findAll() throws IOException {

        ListePosteReponse listePosteReponse = null;

        listePosteReponse = posteApi.findAll().execute().body();

        return listePosteReponse.getPostes();
    }
}
