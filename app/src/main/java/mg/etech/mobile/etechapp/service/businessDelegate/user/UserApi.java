package mg.etech.mobile.etechapp.service.businessDelegate.user;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.LoginUserResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.CreateUserResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.user.request.CreateUserRequest;
import mg.etech.mobile.etechapp.service.businessDelegate.user.request.LoginUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public interface UserApi {

    @Headers("Content-Type: application/json")
    @POST(ConfigUrl.User.CREATE_USER)
    Call<CreateUserResponse> createUser(
            @Body CreateUserRequest request
    );

    @Headers("Content-Type: application/json")
    @POST(ConfigUrl.User.LOG_USER)
    Call<LoginUserResponse> logUser(
            @Body LoginUserRequest request
    );
}
