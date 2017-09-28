package mg.etech.mobile.etechapp.service.businessDelegate.employe;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.CreateEmployeResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.DeleteEmployeResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.ListEmployeResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.reponses.UpdateEmployeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeApi {

    @GET(ConfigUrl.employe.FIND_ALL)
    Call<ListEmployeResponse> findAll();

    @Headers("Content-Type: application/json")
    @POST(ConfigUrl.employe.CREATE_EMPLOYE)
    Call<CreateEmployeResponse> create(
            @Body EmployeWsDto employeWsDtoRequest
    );

    @Headers("Content-Type: application/json")
    @POST(ConfigUrl.employe.UPDATE_EMPLOYE)
    Call<UpdateEmployeResponse> update(
            @Body EmployeWsDto employeWsDtoRequest
    );

    @GET(ConfigUrl.employe.DELETE_EMPLOYE)
    Call<DeleteEmployeResponse> delete(
            @Query("id") Long id
    );


}
