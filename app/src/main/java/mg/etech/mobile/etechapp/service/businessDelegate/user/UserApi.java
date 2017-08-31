package mg.etech.mobile.etechapp.service.businessDelegate.user;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.LoginUserResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.UserCreateResponse;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public interface UserApi {

    @FormUrlEncoded
    @POST(ConfigUrl.User.CREATE_USER)
    UserCreateResponse createUser(
            @Query("firstname") String firstName,
            @Query("lastname") String lastname,
            @Query("login") String login,
            @Query("password") String password,
            @Query("encodedPhoto") String encodedPhoto
    );

    @FormUrlEncoded
    @POST(ConfigUrl.User.LOG_USER)
    LoginUserResponse logUser(
            @Query("login") String login,
            @Query("password") String password
    );
}
