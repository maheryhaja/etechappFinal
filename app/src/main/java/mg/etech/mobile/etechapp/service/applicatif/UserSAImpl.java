package mg.etech.mobile.etechapp.service.applicatif;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.contrainte.factory.dto.UserDtoFromWSFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.UserDtoFromWSFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.service.businessDelegate.user.Userbdl;
import mg.etech.mobile.etechapp.service.businessDelegate.user.UserbdlImpl;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserSAImpl implements UserSA {

    @Bean(UserbdlImpl.class)
    Userbdl userbdl;

    @Bean(UserDtoFromWSFactoryImpl.class)
    UserDtoFromWSFactory userDtoFromWSFactory;

    @Override
    public UserDto logIn(String login, String password) throws LoginFailedException {
        return userDtoFromWSFactory.getInstance(userbdl.logUser(login, password));
    }
}
