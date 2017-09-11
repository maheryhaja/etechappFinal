package mg.etech.mobile.etechapp.contrainte.factory.domainobject.poste;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.domainobject.Poste;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;

/**
 * Created by maheryHaja on 9/11/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PosteFromDtoFactoryImpl extends BaseFactory<PosteDto, Poste> implements PosteFromDtoFactory{
}
