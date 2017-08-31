package mg.etech.mobile.etechapp.service.businessDelegate.user;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingleton;
import mg.etech.mobile.etechapp.contrainte.singleton.RetrofitSingletonImpl;
import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;
import mg.etech.mobile.etechapp.service.businessDelegate.user.reponses.LoginUserResponse;

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
        LoginUserResponse loginUserResponse = userApi.logUser(login, password);
        if(!loginUserResponse.isSuccess()) {
            throw new LoginFailedException();
        }
        return loginUserResponse.getUser();
    }
}
