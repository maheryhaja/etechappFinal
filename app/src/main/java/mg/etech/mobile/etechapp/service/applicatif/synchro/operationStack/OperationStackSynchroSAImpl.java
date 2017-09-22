package mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

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
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.operation.OperationSA;
import mg.etech.mobile.etechapp.service.applicatif.operation.OperationSAImpl;

/**
 * Created by mahery.haja on 21/09/2017.
 */

@EBean(scope = EBean.Scope.Singleton)
public class OperationStackSynchroSAImpl implements OperationStackSynchroSA {

    @Bean(OperationSAImpl.class)
    OperationSA operationSA;

    private int negativeId = -1;

    private Map<Integer, OperationDto> operationDtoMap = new HashMap<>();


    private PublishSubject<OperationDto> addSubject = PublishSubject.create();
    private PublishSubject<OperationDto> removeSubject = PublishSubject.create();

    ReplaySubject<OperationDto> initialPublishSubject = ReplaySubject.create();

    @Override
    public ReplaySubject<OperationDto> getInitialOperationList() {
        return initialPublishSubject;
    }

    @Override
    public Observable<OperationDto<EmployeDto>> getInitialOperationListForEmploye() {
        return initialPublishSubject
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<OperationDto>() {
                    @Override
                    public boolean test(OperationDto operationDto) {
                        Log.d("mahery-haja", "test de filtre " + operationDto.getClassName());
                        return operationDto.getClassName().contains(EmployeDto.class.getName());
                    }
                })
                .map(new Function<OperationDto, OperationDto<EmployeDto>>() {
                    @Override
                    public OperationDto<EmployeDto> apply(@NonNull OperationDto operationDto) throws Exception {
                        Log.d("mahery-haja", "test de filtre success");
                        return operationDto;
                    }
                })
                ;
    }

    @Override
    public void addOperation(OperationDto operationDto) {
        operationSA.create(operationDto);
        operationDtoMap.put((int) operationDto.getId(), operationDto);

        Log.d("mahery-haja", "add operation called");
        addSubject.onNext(operationDto);

    }

    @Override
    public void initialize() {
        Observable
                .fromCallable(new Callable<List<OperationDto>>() {
                    @Override
                    public List<OperationDto> call() throws Exception {
                        return operationSA.findAll();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<OperationDto>, ObservableSource<OperationDto>>() {

                    @Override
                    public ObservableSource<OperationDto> apply(@NonNull List<OperationDto> operationDtos) throws Exception {
                        return Observable
                                .fromIterable(operationDtos);
                    }
                })
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        Log.d("mahery-haja", "call of onNext");
                        operationDtoMap.put((int) operationDto.getId(), operationDto);
                        addSubject.onNext(operationDto);
                        initialPublishSubject.onNext(operationDto);

                    }
                })
        ;
    }


    @Override
    public void deleteOperation(OperationDto operationDto) {

    }

    @Override
    public Observable<OperationDto> getActualList() {
        return Observable
                .fromIterable(operationDtoMap.values());
    }

    @Override
    public Observable<OperationDto> onAddObservable() {
        return addSubject;
    }

    @Override
    public void notifySuccess(OperationDto operationDto) {
        operationSA.deleteById(operationDto.getId());
        operationDtoMap.remove((int) operationDto.getId());
        removeSubject.onNext(operationDto);
    }

    @Override
    public void notifyError(OperationDto operationDto) {

    }

    @Override
    public Observable<OperationDto> onDeleteObservable() {
        return removeSubject;
    }
}
