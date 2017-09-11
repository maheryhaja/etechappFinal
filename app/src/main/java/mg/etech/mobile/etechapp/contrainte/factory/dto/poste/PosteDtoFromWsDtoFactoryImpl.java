package mg.etech.mobile.etechapp.contrainte.factory.dto.poste;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;
import mg.etech.mobile.etechapp.donnee.wsdto.PosteWsDto;

/**
 * Created by maheryHaja on 9/11/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PosteDtoFromWsDtoFactoryImpl extends BaseFactory<PosteWsDto, PosteDto> implements PosteDtoFromWsDtoFactory {
}
