package mg.etech.mobile.etechapp.contrainte.factory.domainobject;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleFromDtoFactoryImpl extends BaseFactory<PoleDto, Pole> implements PoleFromDtoFactory {
}
