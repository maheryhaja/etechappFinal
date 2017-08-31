package mg.etech.mobile.etechapp.service.applicatif;

import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;

/**
 * Created by mahery.haja on 31/08/2017.
 */
public interface UserSA {
    UserDto logIn(String login, String password) throws LoginFailedException;
}
