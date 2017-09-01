package mg.etech.mobile.etechapp.commun.exception.user;

/**
 * Created by mahery.haja on 31/08/2017.
 */

public class LoginFailedException extends Exception{

    @Override
    public String getMessage() {
        return "Login Failed exception";
    }
}
