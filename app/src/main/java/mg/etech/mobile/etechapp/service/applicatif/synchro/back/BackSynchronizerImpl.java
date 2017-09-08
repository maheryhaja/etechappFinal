package mg.etech.mobile.etechapp.service.applicatif.synchro.back;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.factory.dto.EmployeDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.EmployeDtoFromWsDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.PoleDtoFromWSFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.PoleDtoFromWSFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.PoleBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.PoleBdlImpl;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean
public class BackSynchronizerImpl implements BackSynchronizerSA {

    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    @Bean(PoleBdlImpl.class)
    PoleBdl poleBdl;

    @Bean(EmployeBdlImpl.class)
    EmployeBdl employeBdl;

    @Bean(PoleDtoFromWSFactoryImpl.class)
    PoleDtoFromWSFactory poleDtoFromWSFactory;

    @Bean(EmployeDtoFromWsDtoFactoryImpl.class)
    EmployeDtoFromWsDtoFactory employeDtoFromWsDtoFactory;

    @Override
    public void synch() throws IOException, ApiCallException {
        clearAllTable();
        populateBase();
    }

    // effacer: pole, employe
    public void clearAllTable() {
        poleSA.deleteAll();
        employeSA.deleteAll();
    }


    public List<PoleDto> retrieveAllPole() throws IOException, ApiCallException {
        return poleDtoFromWSFactory.getInstance(poleBdl.findAll());
    }

    public List<EmployeDto> retrieveAllEmploye(List<PoleDto> poleDtos) throws IOException, ApiCallException {
        Map<Long, PoleDto> poleDtoMap = new HashMap<>();
        for (PoleDto poleDto : poleDtos) {
            poleDtoMap.put(poleDto.getId(), poleDto);
        }

        List<EmployeWsDto> employeWsDtos = employeBdl.findAll();

        List<EmployeDto> employeDtos = new ArrayList<>();

        for (EmployeWsDto employeWsDto : employeWsDtos) {
            employeDtos.add(employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDto, poleDtoMap.get(employeWsDto.getId())));
        }
        return employeDtos;
    }

    public void populateBase() throws IOException, ApiCallException {
        List<PoleDto> poleDtos = retrieveAllPole();
        poleSA.create(poleDtos);
        poleDtos = poleSA.findAll();
        List<EmployeDto> employeDtos = retrieveAllEmploye(poleDtos);


        employeSA.create(employeDtos);
    }

}
