package mg.etech.mobile.etechapp.service.applicatif.synchro.database;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
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
    private Map<Integer, EmployeDto> employeDtomap = new HashMap<>();

    private PublishSubject<EmployeDto> addSubject = PublishSubject.create();
    private PublishSubject<EmployeDto> updateSubject = PublishSubject.create();
    private PublishSubject<EmployeDto> deleteSubject = PublishSubject.create();


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
                                   employeDtomap.put(employeDto.getId().intValue(), employeDto);
                                   initialEmployeSubject.onNext(employeDto);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("mahery-haja", "retrieve error");
                                throwable.printStackTrace();
                            }
                        }

                );
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


    @Override
    public Observable<EmployeDto> getActualEmployeListObservable() {
        return Observable
                .fromIterable(this.employeDtomap.values())
                ;
    }

    @Override
    public List<EmployeDto> getActualList() {
        return new ArrayList<>(employeDtomap.values());
    }

    @Override
    public void addEmploye(EmployeDto employeDtoToCreate) {
        Observable
                .just(employeDtoToCreate)
                .map(new Function<EmployeDto, EmployeDto>() {
                    @Override
                    public EmployeDto apply(@NonNull EmployeDto empDto) throws Exception {
                        employeSA.create(empDto);
                        return empDto;
                    }
                })
                .subscribe(new Consumer<EmployeDto>() {

                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        // on success
                        employeDtomap.put(employeDto.getId().intValue(), employeDto);
                        addSubject.onNext(employeDto);
                    }
                });
    }

    @Override
    public void deleteEmploye(EmployeDto employeDtoToDelete) {
        Observable
                .just(employeDtoToDelete)
                .map(new Function<EmployeDto, EmployeDto>() {
                    @Override
                    public EmployeDto apply(@NonNull EmployeDto employeDto) throws Exception {
                        employeSA.deleteById(employeDto.getId());
                        return employeDto;
                    }
                })
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        employeDtomap.remove(employeDto.getId().intValue());
                        deleteSubject.onNext(employeDto);
                    }
                });
    }

    @Override
    public void updateEmploye(final EmployeDto data, final EmployeDto target) {
        Observable
                .just(data)
                .map(new Function<EmployeDto, EmployeDto>() {
                    @Override
                    public EmployeDto apply(@NonNull EmployeDto employeDto) throws Exception {
                        employeSA.update(employeDto);
                        Log.d("mahery-haja", "update done");
                        return employeDto;
                    }
                })
                .subscribe(new Consumer<EmployeDto>() {
                    // on delete success
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        employeDtomap.put(employeDto.getId().intValue(), employeDto);

                        if (data.getPole().getId() == target.getPole().getId()) {
                            updateSubject.onNext(employeDto);
                        } else {
                            // changement de pole: supprimer puis creer pour permettre le bon affichage :)
                            deleteSubject.onNext(data);
                            addSubject.onNext(employeDto);
                        }
                    }
                });
    }

    @Override
    public Observable<EmployeDto> onAddObservable() {
        return addSubject;
    }

    @Override
    public Observable<EmployeDto> onDeleteObservable() {
        return deleteSubject;
    }

    @Override
    public Observable<EmployeDto> onUpdateObservable() {
        return updateSubject;
    }
}
