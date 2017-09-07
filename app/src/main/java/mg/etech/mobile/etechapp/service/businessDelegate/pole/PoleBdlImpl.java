package mg.etech.mobile.etechapp.service.businessDelegate.pole;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.wsdto.PoleWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.reponses.ListPoleResponse;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleBdlImpl implements PoleBdl {

    PoleApi poleApi;

    @Bean(RetrofitSingletonImpl.class)
    RetrofitSingleton retrofitSingleton;

    @AfterInject
    void initAfterInject() {
        poleApi = retrofitSingleton.getDefaultForRx().create(PoleApi.class);
    }

    @Override
    public List<PoleWsDto> findAll() throws IOException, ApiCallException {

        ListPoleResponse listPoleResponse = poleApi.findAll().execute().body();

        List<PoleWsDto> poleWsDtos = null;
        if (!listPoleResponse.isSuccess()) {

            throw new ApiCallException();
        } else {

            poleWsDtos = listPoleResponse.getDatas();
        }
        return poleWsDtos;
    }
}
