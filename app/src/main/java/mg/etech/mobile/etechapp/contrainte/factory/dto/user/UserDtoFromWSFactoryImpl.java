package mg.etech.mobile.etechapp.contrainte.factory.dto.user;

import org.androidannotations.annotations.EBean;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.donnee.wsdto.UserWsDto;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserDtoFromWSFactoryImpl implements UserDtoFromWSFactory {

    @Override
    public UserDto getInstance() {
        return new UserDto();
    }

    @Override
    public UserDto getInstance(UserWsDto dObj) {
        return new ModelMapper().map(dObj, UserDto.class);
    }

    @Override
    public List<UserDto> getInstance(Collection<UserWsDto> dObjs) {
        List<UserDto> userDtos = new ArrayList<>();
        for(UserWsDto dObj:dObjs)
            userDtos.add(getInstance(dObj));
        return userDtos;
    }
}
