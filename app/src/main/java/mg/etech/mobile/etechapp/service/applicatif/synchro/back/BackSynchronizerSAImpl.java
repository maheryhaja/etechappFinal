package mg.etech.mobile.etechapp.service.applicatif.synchro.back;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromWSFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.pole.PoleDtoFromWSFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromDOFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromDOFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.PosteDtoFromWsDtoFactoryImpl;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSA;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSAImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.PoleBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.pole.PoleBdlImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.poste.PosteBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.poste.PosteBdlImpl;

/**
 * Created by mahery.haja on 07/09/2017.
 */
@EBean
public class BackSynchronizerSAImpl implements BackSynchronizerSA {

    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    @Bean(PoleBdlImpl.class)
    PoleBdl poleBdl;

    @Bean(EmployeBdlImpl.class)
    EmployeBdl employeBdl;

    @Bean(PosteBdlImpl.class)
    PosteBdl posteBdl;


    @Bean(PosteDtoFromDOFactoryImpl.class)
    PosteDtoFromDOFactory posteDtoFromDOFactory;

    @Bean(PosteDtoFromWsDtoFactoryImpl.class)
    PosteDtoFromWsDtoFactory posteDtoFromWsDtoFactory;

    @Bean(PosteSAImpl.class)
    PosteSA posteSA;

    @Bean(PoleDtoFromWSFactoryImpl.class)
    PoleDtoFromWSFactory poleDtoFromWSFactory;


    @Bean(EmployeDtoFromWsDtoFactoryImpl.class)
    EmployeDtoFromWsDtoFactory employeDtoFromWsDtoFactory;

    @Bean(EmployeWsDtoFromDtoFactoryImpl.class)
    EmployeWsDtoFromDtoFactory employeWsDtoFromDtoFactory;

    @Override
    public void synch() throws IOException, ApiCallException {
        clearAllTable();
        populateBase();
    }

    @Override
    public EmployeDto createEmploye(EmployeDto nouveauEmployeDto) throws IOException, ApiCallException {
        EmployeWsDto employeWsDto = employeBdl.create(employeWsDtoFromDtoFactory.getInstance(nouveauEmployeDto));
        EmployeDto employeDto = employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDto, nouveauEmployeDto.getPole());
        employeSA.create(employeDto);
        return employeDto;
    }

    // effacer: pole, employe
    public void clearAllTable() {
        Log.d("mahery-haja", "synch: clear begin");
        Log.d("mahery-haja", "synch: deletePoleSa");
        poleSA.deleteAll();
        Log.d("mahery-haja", "synch: delete employe");
        employeSA.deleteAll();

        posteSA.deleteAll();
        Log.d("mahery-haja", "synch: clear tables");
    }


    public List<PoleDto> retrieveAllPole() throws IOException, ApiCallException {
        return poleDtoFromWSFactory.getInstance(poleBdl.findAll());
    }

    public List<PosteDto> retrieveAllPoste() throws IOException {
        return posteDtoFromWsDtoFactory.getInstance(posteBdl.findAll());
    }

    public List<EmployeDto> retrieveAllEmploye(List<PoleDto> poleDtos) throws IOException, ApiCallException {
        Map<Long, PoleDto> poleDtoMap = new HashMap<>();
        for (PoleDto poleDto : poleDtos) {
            poleDtoMap.put(poleDto.getId(), poleDto);
        }

        List<EmployeWsDto> employeWsDtos = employeBdl.findAll();

        List<EmployeDto> employeDtos = new ArrayList<>();

        for (EmployeWsDto employeWsDto : employeWsDtos) {

            try {
                if (employeWsDto.getPole() < poleDtos.size()) {
                    //pour eviter les erreurs de pole
                    employeDtos.add(employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDto, poleDtoMap.get(employeWsDto.getPole())));
                }
            } catch (NullPointerException e) {
                //pole null
            }

        }
        return employeDtos;
    }


    public void populateBase() throws IOException, ApiCallException {
        List<PoleDto> poleDtos = retrieveAllPole();
        poleSA.create(poleDtos);

        List<PosteDto> posteDtos = retrieveAllPoste();
        posteSA.create(posteDtos);


        poleDtos = poleSA.findAll();
        List<EmployeDto> employeDtos = retrieveAllEmploye(poleDtos);


        employeSA.create(employeDtos);
    }

}
