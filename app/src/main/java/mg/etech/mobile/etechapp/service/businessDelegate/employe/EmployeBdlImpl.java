package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.ListEmployeResponse;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeBdlImpl implements EmployeBdl {

    @Bean(RetrofitSingletonImpl.class)
    RetrofitSingleton retrofitSingleton;


    private EmployeApi employeApi;

    @AfterInject
    public void initAfterInject() {
        employeApi = retrofitSingleton.getDefaultForRx().create(EmployeApi.class);
    }

    @Override
    public List<EmployeWsDto> findAll() throws IOException, ApiCallException {

        ListEmployeResponse listEmployeResponse = employeApi.findAll().execute().body();

        List<EmployeWsDto> employeWsDtos = null;

        if (!listEmployeResponse.isSuccess()) {
            throw new ApiCallException();
        } else {
            employeWsDtos = listEmployeResponse.getDatas();
        }
        return employeWsDtos;
    }
}
