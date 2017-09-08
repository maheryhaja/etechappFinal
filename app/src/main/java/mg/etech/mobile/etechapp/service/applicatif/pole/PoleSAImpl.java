package mg.etech.mobile.etechapp.service.applicatif.pole;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

import mg.etech.mobile.etechapp.contrainte.factory.domainobject.PoleFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.domainobject.PoleFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.PoleDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.PoleDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.service.Metier.pole.CreateUpdateDeletePoleSM;
import mg.etech.mobile.etechapp.service.Metier.pole.CreateUpdateDeletePoleSMImpl;
import mg.etech.mobile.etechapp.service.Metier.pole.RetrievePoleSM;
import mg.etech.mobile.etechapp.service.Metier.pole.RetrievePoleSMImpl;

/**
 * Created by mahery.haja on 06/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleSAImpl implements PoleSA {


    @Bean(RetrievePoleSMImpl.class)
    RetrievePoleSM retrievePoleSM;

    @Bean(PoleDtoFromDOFactoryImpl.class)
    PoleDtoFromDOFactory poleDtoFromDOFactory;


    @Bean(CreateUpdateDeletePoleSMImpl.class)
    CreateUpdateDeletePoleSM createUpdateDeletePoleSM;

    @Bean(PoleFromDtoFactoryImpl.class)
    PoleFromDtoFactory poleFromDtoFactory;

    @Override
    public List<PoleDto> findAll() {
        return poleDtoFromDOFactory.getInstance(retrievePoleSM.findAll());
    }

    @Override
    public void create(PoleDto poleDto) {
        createUpdateDeletePoleSM.create(poleFromDtoFactory.getInstance(poleDto));
    }

    @Override
    public void deleteAll() {
        createUpdateDeletePoleSM.deleteAll();
    }

    @Override
    public void create(List<PoleDto> poleDtos) {
        createUpdateDeletePoleSM.create(poleFromDtoFactory.getInstance(poleDtos));
    }
}
