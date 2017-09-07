package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.ListEmployeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeApi {

    @GET(ConfigUrl.employe.FIND_ALL)
    Call<ListEmployeResponse> findAll();

}
