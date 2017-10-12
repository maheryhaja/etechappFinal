package mg.etech.mobile.etechapp.service.applicatif.synchro.pull;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl;

/**
 * Created by mahery.haja on 25/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PullSynchroSAImpl implements PullSynchroSA {

    @Bean(EmployeBdlImpl.class)
    EmployeBdl employeBdl;

    @Bean(DataBaseSynchroSAImpl.class)
    DataBaseSynchroSA dataBaseSynchroSA;

    @Bean(EmployeDtoFromWsDtoFactoryImpl.class)
    EmployeDtoFromWsDtoFactory employeDtoFromWsDtoFactory;

    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    private BehaviorSubject<Boolean> runningObservable = BehaviorSubject.create();

    @AfterInject
    void initAfterInject() {
        runningObservable.onNext(false);
    }

    @Override
    public Observable<Boolean> getRunningObservable() {
        return runningObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public void launch() {
        // retrieve all employe from database
        Log.d("mahery-haja", "pull begin");
        runningObservable.onNext(true);
        Observable
                .fromCallable(new Callable<List<EmployeDto>>() {
                    @Override
                    public List<EmployeDto> call() throws Exception {
                        Log.d("mahery-haja", "retrieve employe from bdl");
                        return retrieveAllEmploye();
                    }
                })
                .map(new Function<List<EmployeDto>, List<EmployeDto>>() {
                    @Override
                    public List<EmployeDto> apply(@NonNull List<EmployeDto> nouveauEmployeDtos) throws Exception {

                        //retrieve actual Bdd
                        Log.d("mahery-haja", "retrieve employe from database");
                        List<EmployeDto> actualEmployeList = dataBaseSynchroSA.getActualList();

                        Log.d("mahery-haja", "actual liste size" + actualEmployeList.size());

                        Set<Long> actualIdSet = new HashSet<>();
                        HashMap<Long, EmployeDto> actualEmployeDtoMap = new HashMap<>();

                        for (EmployeDto employeDto : actualEmployeList) {
                            actualEmployeDtoMap.put(employeDto.getId(), employeDto);
                        }
                        for (EmployeDto employeDto : actualEmployeList) {
                            actualIdSet.add(employeDto.getId());
                        }

                        // for new Employe
                        Map<Long, EmployeDto> nouveauEmployeMap = new HashMap<>();
                        Set<Long> nouveauIdSet = new HashSet<>();

                        for (EmployeDto employeDto : nouveauEmployeDtos) {
                            nouveauEmployeMap.put(employeDto.getId(), employeDto);
                        }

                        for (EmployeDto employeDto : nouveauEmployeMap.values()) {
                            nouveauIdSet.add(employeDto.getId());
                        }
                        Log.d("mahery-haja", "new employe size " + nouveauEmployeDtos.size());
                        if (nouveauEmployeDtos.size() == 0)
                            throw new ApiCallException();
                        // elements need to be add
                        Set<Long> addSet = new HashSet<>();
                        addSet.addAll(nouveauIdSet);
                        addSet.removeAll(actualIdSet);

                        for (Long id : addSet) {
                            Log.d("mahery-haja", "pull:add element " + id);
                            dataBaseSynchroSA.addEmploye(nouveauEmployeMap.get(id));
                        }

                        //elements need to be delete
                        Set<Long> removeSet = new HashSet<>();
                        removeSet.addAll(actualIdSet);
                        removeSet.removeAll(nouveauIdSet);

                        for (Long id : removeSet) {
                            Log.d("mahery-haja", "pull:remove element " + id);
                            dataBaseSynchroSA.deleteEmploye(actualEmployeDtoMap.get(id));
                        }

                        //elements need to be update
                        Set<Long> updateElement = new HashSet<>();
                        updateElement.addAll(actualIdSet);
                        updateElement.retainAll(nouveauIdSet);

                        for (Long id : updateElement) {
                            if (!actualEmployeDtoMap.get(id).equals(nouveauEmployeMap.get(id))) {
                                Log.d("mahery-haja", "pull:update element " + id);
                                dataBaseSynchroSA.updateEmploye(nouveauEmployeMap.get(id), actualEmployeDtoMap.get(id));
                            }
                        }

                        return nouveauEmployeDtos;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<EmployeDto>>() {
                               @Override
                               public void accept(List<EmployeDto> nouveauEmployeDtos) throws Exception {

                                   runningObservable.onNext(false);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                runningObservable.onNext(false);
                                if (throwable instanceof ApiCallException) {
                                    Log.d("mahery-haja", "api call exception");
                                } else
                                    throwable.printStackTrace();
                            }
                        }
                );


    }

    private List<EmployeDto> retrieveAllEmploye() throws IOException, ApiCallException {

        Map<Long, PoleDto> poleDtoMap = new HashMap<>();
        List<EmployeDto> employeDtos = new ArrayList<>();


        for (PoleDto poleDto : poleSA.findAll()) {
            poleDtoMap.put(poleDto.getId(), poleDto);
        }

        for (EmployeWsDto employeWsDto : employeBdl.findAll()) {
            employeDtos.add(employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDto, poleDtoMap.get(employeWsDto.getPole())));
        }
        return employeDtos;
    }

}
