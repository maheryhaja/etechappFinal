package mg.etech.mobile.etechapp.service.applicatif.synchro.database;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;

/**
 * Created by mahery.haja on 21/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataBaseSynchroSAImpl implements DataBaseSynchroSA {

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    //se souvenir de la liste synchronisé des employé tout au long de l'application
    List<EmployeDto> employeDtoList = new ArrayList<>();



    ReplaySubject<EmployeDto> initialEmployeSubject = ReplaySubject.create();

    @Override
    public ReplaySubject<EmployeDto> getInitialEmployeList() {
        return initialEmployeSubject;
    }

    @Override
    public void initialize() {
        Log.d("mahery-haja", "initialisation de Database Synchro");

        retrieveinitialEmployeList()
                .flatMap(new Function<List<EmployeDto>, ObservableSource<EmployeDto>>() {

                    @Override
                    public ObservableSource<EmployeDto> apply(@NonNull List<EmployeDto> employeDtos) throws Exception {
                        return Observable.fromIterable(employeDtos);
                    }
                })
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        initialEmployeSubject.onNext(employeDto);
                    }
                });
    }


    private Observable<List<EmployeDto>> retrieveinitialEmployeList() {
        return Observable
                .fromCallable(new Callable<List<EmployeDto>>() {
                    @Override
                    public List<EmployeDto> call() throws Exception {
                        return employeSA.findAll();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
