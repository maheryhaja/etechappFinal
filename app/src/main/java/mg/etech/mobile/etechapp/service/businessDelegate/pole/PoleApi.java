package mg.etech.mobile.etechapp.service.businessDelegate.pole;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.reponses.ListPoleResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mahery.haja on 06/09/2017.
 */
public interface PoleApi {

    @GET(ConfigUrl.Pole.FIND_ALL)
    Call<ListPoleResponse> findAll();
}
