package mg.etech.mobile.etechapp.contrainte.factory.dto.pole;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.PoleWsDto;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleDtoFromWSFactoryImpl extends BaseFactory<PoleWsDto, PoleDto> implements PoleDtoFromWSFactory {

}
