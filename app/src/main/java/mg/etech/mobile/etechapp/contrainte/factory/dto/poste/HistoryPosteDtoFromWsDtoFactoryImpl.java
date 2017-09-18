package mg.etech.mobile.etechapp.contrainte.factory.dto.poste;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.contrainte.factory.BaseFactory;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.wsdto.HistoryPosteWsDto;

/**
 * Created by mahery.haja on 18/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class HistoryPosteDtoFromWsDtoFactoryImpl extends BaseFactory<HistoryPosteWsDto, HistoryPosteDto>
        implements HistoryPosteDtoFromWsDtoFactory {

}
