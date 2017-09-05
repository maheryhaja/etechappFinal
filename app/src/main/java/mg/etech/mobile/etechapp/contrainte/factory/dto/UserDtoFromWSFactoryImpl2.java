package mg.etech.mobile.etechapp.contrainte.factory.dto;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserDtoFromWSFactoryImpl2 extends BaseFactory<UserWsDto, UserDto> implements UserDtoFromWSFactory {

}
