package mg.etech.mobile.etechapp.service.businessDelegate.user;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.CreateUserResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.LoginUserResponse;
import mg.etech.mobile.etechapp.service.businessDelegate.user.request.CreateUserRequest;
import mg.etech.mobile.etechapp.service.businessDelegate.user.request.LoginUserRequest;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserbdlImpl implements Userbdl{

    UserApi userApi;

    @Bean(RetrofitSingletonImpl.class)
    RetrofitSingleton retrofitSingleton;

    public UserbdlImpl() {

    }

    @AfterInject
    void initAfterInject(){
        userApi = retrofitSingleton.getDefaultForRx().create(UserApi.class);
    }

    @Override
    public UserWsDto logUser(String login, String password) throws LoginFailedException {
        LoginUserResponse loginUserResponse = null;
        try {
            loginUserResponse = userApi.logUser(new LoginUserRequest(login, password)).execute().body();
            Log.d("mahery-haja", "message:"+ loginUserResponse.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!loginUserResponse.isSuccess()) {
            throw new LoginFailedException();
        }
        return loginUserResponse.getUser();
    }

    @Override
    public UserWsDto createUser(UserDto userDto) {
        CreateUserResponse createUserResponse = null;
        try {
            createUserResponse = userApi.createUser(
                    new CreateUserRequest(
                            userDto.getFirstName(),
                            userDto.getLastname(),
                            userDto.getPassword(),
                            userDto.getLogin(),
                            userDto.getPhoto()
                    )
            ).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createUserResponse.getUser();
    }
}
