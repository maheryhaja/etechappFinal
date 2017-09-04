package mg.etech.mobile.etechapp.service.businessDelegate.user;

import mg.etech.mobile.etechapp.commun.exception.user.CreateUserFailedException;
import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public interface Userbdl {
    UserWsDto logUser(String login, String password) throws LoginFailedException;

    UserWsDto createUser(UserDto userDto) throws CreateUserFailedException;
}
