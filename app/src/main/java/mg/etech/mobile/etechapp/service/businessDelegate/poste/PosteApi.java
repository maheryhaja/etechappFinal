package mg.etech.mobile.etechapp.service.businessDelegate.poste;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.service.businessDelegate.poste.reponses.ListePosteReponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mahery.haja on 11/09/2017.
 */

public interface PosteApi {

    @GET(ConfigUrl.poste.FIND_ALL)
    Call<ListePosteReponse> findAll();

}
