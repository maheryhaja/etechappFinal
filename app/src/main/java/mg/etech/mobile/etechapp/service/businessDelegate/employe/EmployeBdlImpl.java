package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.CreateEmployeResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.ListEmployeResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.UpdateEmployeResponse;

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
        Log.d("mahery-haja", "retrieve employe begin");
        List<EmployeWsDto> employeWsDtos = new ArrayList<>();
        try {
            ListEmployeResponse listEmployeResponse = null;
            listEmployeResponse = employeApi.findAll().execute().body();

            if (!listEmployeResponse.isSuccess()) {
                throw new ApiCallException();
            } else {
                employeWsDtos = listEmployeResponse.getDatas();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mahery-haja", "une exception dans creation des employés");
        }

        Log.d("mahery-haja", "bug point " + employeWsDtos.size());
        return employeWsDtos;
    }

    @Override
    public EmployeWsDto create(EmployeWsDto employeWsDto) throws IOException, ApiCallException {
        CreateEmployeResponse createEmployeResponse = employeApi.create(employeWsDto).execute().body();
        EmployeWsDto employeWsDto1 = null;

        if (!createEmployeResponse.isSuccess()) {
            throw new ApiCallException();
        } else {
            // success case
            employeWsDto1 = createEmployeResponse.getEmploye();
        }
        return employeWsDto1;
    }

    @Override
    public EmployeWsDto update(EmployeWsDto employeWsDto) throws IOException, ApiCallException {

        UpdateEmployeResponse updateEmployeResponse = employeApi.update(employeWsDto).execute().body();
        EmployeWsDto updatedEmployeWsDto = null;

        if (!updateEmployeResponse.isSuccess()) {
            throw new ApiCallException();
        } else {
            updatedEmployeWsDto = updateEmployeResponse.getEmploye();
        }

        return updatedEmployeWsDto;
    }
}
