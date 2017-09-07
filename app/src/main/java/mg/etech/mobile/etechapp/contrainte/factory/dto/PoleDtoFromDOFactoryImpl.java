package mg.etech.mobile.etechapp.contrainte.factory.dto;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleDtoFromDOFactoryImpl extends BaseFactory<Pole, PoleDto> implements PoleDtoFromDOFactory {
}
