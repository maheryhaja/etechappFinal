package mg.etech.mobile.etechapp.service.applicatif.synchro.central;


import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItem;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItemTemp;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;

/**
 * Created by mahery.haja on 22/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CentralEmployeSynchroSAImpl implements CentralEmployeSynchroSA {

    private List<SuperListEmployeItem> itemList = new ArrayList<>();
    private Map<Integer, SuperListEmployeItem> itemMap = new HashMap<>();

    private PublishSubject<SuperListEmployeItem> addSubject = PublishSubject.create();
    private PublishSubject<SuperListEmployeItem> updateSubject = PublishSubject.create();
    private PublishSubject<SuperListEmployeItem> deleteSubject = PublishSubject.create();

    private int positiveid;
    private int negativeId;


    @Bean(DataBaseSynchroSAImpl.class)
    DataBaseSynchroSA dataBaseSynchroSA;

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;
    private Predicate<OperationDto> filtre = new Predicate<OperationDto>() {
        @Override
        public boolean test(@NonNull OperationDto operationDto) throws Exception {

            Log.d("mahery-haja", "passage filtre " + operationDto.getClassName());
            return operationDto.getClassName().contains(EmployeDto.class.getName());
        }
    };


    @Override
    public void initialize() {
        //retrieve from the two database

        dataBaseSynchroSA.getInitialEmployeList()
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        ListEmployeItem listEmployeItem = createListEmployeItem(employeDto);
                        addSubject.onNext(listEmployeItem);
                    }
                });

        operationStackSynchroSA
                .getInitialOperationListForEmploye()
                .subscribe(new Consumer<OperationDto<EmployeDto>>() {
                    @Override
                    public void accept(OperationDto<EmployeDto> employeDtoOperationDto) throws Exception {
                        ListEmployeItemTemp listEmployeItemTemp = createListEmployeItemTemp(employeDtoOperationDto);
                        addSubject.onNext(listEmployeItemTemp);
                    }
                });


        operationStackSynchroSA
                .onAddObservable()
                .filter(filtre)
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        addSubject.onNext(createListEmployeItemTemp(operationDto));
                    }
                });

        operationStackSynchroSA
                .onDeleteObservable()
                .filter(filtre)
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        int id = (int) operationDto.getId();
                        id = id * -1;
                        deleteSubject.onNext(itemMap.get(id));
                        itemMap.remove(id);
                    }
                });

    }

    @android.support.annotation.NonNull
    private ListEmployeItemTemp createListEmployeItemTemp(OperationDto<EmployeDto> employeDtoOperationDto) {
        ListEmployeItemTemp listEmployeItemTemp = new ListEmployeItemTemp(employeDtoOperationDto.getData());
        int id = (int) employeDtoOperationDto.getId();
        id = id * -1;
        listEmployeItemTemp.setItemId(id);
        Log.d("mahery-haja", "created Id from Central " + id);
        itemMap.put(id, listEmployeItemTemp);
        return listEmployeItemTemp;
    }

    @android.support.annotation.NonNull
    private ListEmployeItem createListEmployeItem(EmployeDto employeDto) {
        ListEmployeItem listEmployeItem = new ListEmployeItem(employeDto);
        int id = employeDto.getId().intValue();
        listEmployeItem.setItemId(employeDto.getId().intValue());
        itemMap.put(id, listEmployeItem);
        return listEmployeItem;
    }

    @Override
    public Observable<SuperListEmployeItem> getActualList() {
        return Observable
                .fromIterable(itemMap.values())
                ;
    }

    @Override
    public Observable<SuperListEmployeItem> onAddObservable() {
        return addSubject;
    }

    @Override
    public Observable<SuperListEmployeItem> onDeleteObservable() {
        return deleteSubject;
    }

    @Override
    public Observable<SuperListEmployeItem> onUpdateObservable() {
        return updateSubject;
    }

    @Override
    public EmployeDto findByitemId(int id) {
        return itemMap.get(id).getEmployeDto();
    }
}
