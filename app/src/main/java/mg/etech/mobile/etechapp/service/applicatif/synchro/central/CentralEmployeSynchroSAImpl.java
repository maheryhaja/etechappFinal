package mg.etech.mobile.etechapp.service.applicatif.synchro.central;


import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItem;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItemTemp;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;
import mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker.CommandInvoker;
import mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker.CommandInvokerImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;

/**
 * Created by mahery.haja on 22/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class

CentralEmployeSynchroSAImpl implements CentralEmployeSynchroSA {

    private List<SuperListEmployeItem> itemList = new ArrayList<>();
    private Map<Integer, SuperListEmployeItem> itemMap = new HashMap<>();

    private PublishSubject<SuperListEmployeItem> addSubject = PublishSubject.create();
    private PublishSubject<SuperListEmployeItem> updateSubject = PublishSubject.create();
    private PublishSubject<SuperListEmployeItem> deleteSubject = PublishSubject.create();
    private PublishSubject<ItemReplacement> replaceSubject = PublishSubject.create();

    private PublishSubject<SuperListEmployeItem> processSubject = PublishSubject.create();
    private PublishSubject<SuperListEmployeItem> errorProcessSubject = PublishSubject.create();


    @Bean(DataBaseSynchroSAImpl.class)
    DataBaseSynchroSA dataBaseSynchroSA;

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;

    @Bean(CommandInvokerImpl.class)
    CommandInvoker commandInvoker;

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

        dataBaseSynchroSA
                .onAddObservable()
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        createListEmployeItem(employeDto);
                    }
                });

        dataBaseSynchroSA
                .onUpdateObservable()
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        createListEmployeItem(employeDto);
                    }
                });

        dataBaseSynchroSA
                .onDeleteObservable()
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
                        int id = employeDto.getId().intValue();
                        deleteSubject.onNext(itemMap.get(id));
                        itemMap.remove(id);
                    }
                });

        operationStackSynchroSA
                .getInitialOperationListForEmploye()
                .subscribe(new Consumer<OperationDto<EmployeDto>>() {
                    @Override
                    public void accept(OperationDto<EmployeDto> employeDtoOperationDto) throws Exception {
                        ListEmployeItemTemp listEmployeItemTemp = createListEmployeItemTemp(employeDtoOperationDto);
                        //               addSubject.onNext(listEmployeItemTemp);
                    }
                });


        operationStackSynchroSA
                .onAddObservable()
                .filter(filtre)
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        // pour la creation d'un employe
                        ListEmployeItemTemp listEmployeItemTemp = createListEmployeItemTemp(operationDto);

                        //pour la modeification d'un employe
                    }
                });

        operationStackSynchroSA
                .onUpdateObservable()
                .filter(filtre)
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        ListEmployeItemTemp nouveaItemTemp = fromOperationDto(operationDto);
                        int itemId = nouveaItemTemp.getItemId();
                        if (itemMap.keySet().contains(itemId)) {
                            // element present: juste une

                            //   check if changement de pole
                            SuperListEmployeItem ancienItem = itemMap.get(itemId);

                            replaceSubject.onNext(new ItemReplacement(ancienItem, nouveaItemTemp));
                            itemMap.put(itemId, nouveaItemTemp);

                        }
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


                        if (operationDto.getOperationName().equals(OperationType.DELETE) || operationDto.getOperationName().equals(OperationType.UPDATE)) {

                            //remplacer par la valeur d'origine
                            EmployeDto target = (EmployeDto) operationDto.getTarget();
                            EmployeDto data = (EmployeDto) operationDto.getData();
                            ListEmployeItem listEmployeItem = new ListEmployeItem(target != null ? target : data);

                            try {
                                listEmployeItem.setItemId(target.getId().intValue());
                            } catch (NullPointerException e) {
                                // pas de target ---> delete operation
                                Log.d("mahery-haja", "process delete operation");
                                listEmployeItem.setItemId(data.getId().intValue());
                            }
                            replaceSubject.onNext(new ItemReplacement(itemMap.get(id), listEmployeItem));
                            itemMap.put(listEmployeItem.getItemId(), listEmployeItem);
                            itemMap.remove(id);
                        } else {
                            deleteSubject.onNext(itemMap.get(id));
                            itemMap.remove(id);

                        }

                    }
                });

        operationStackSynchroSA
                .onSucceedObservable()
                .filter(filtre)
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        EmployeDto data = (EmployeDto) operationDto.getData();
                        EmployeDto target = (EmployeDto) operationDto.getTarget();
                        int id = (int) operationDto.getId();
                        id = id * -1;

                        Log.d("mahery-haja", "success of " + operationDto.getOperationName());

                        if (operationDto.getOperationName().equals(OperationType.CREATE)) {
                            ListEmployeItem listEmployeItem = new ListEmployeItem(data);
                            listEmployeItem.setItemId(data.getId().intValue());
                            itemMap.put(listEmployeItem.getItemId(), listEmployeItem);
                            Log.d("mahery-haja", "trigger replacement after create");
                            replaceSubject.onNext(new ItemReplacement(itemMap.get(id), listEmployeItem));
                        }

                        if (operationDto.getOperationName().equals(OperationType.UPDATE)) {
                            ListEmployeItem listEmployeItem = new ListEmployeItem(data);
                            listEmployeItem.setItemId(data.getId().intValue());
                            itemMap.put(listEmployeItem.getItemId(), listEmployeItem);
                            replaceSubject.onNext(new ItemReplacement(itemMap.get(id), listEmployeItem));
                        }

                        if (operationDto.getOperationName().equals(OperationType.DELETE)) {
                            deleteSubject.onNext(itemMap.get(id));
                        }
                        // for delete
                        itemMap.remove(id);
                    }
                });


        commandInvoker
                .onProceessingObservable()
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {

                        Log.d("mahery-haja", "try to process " + operationDto.getId());

                        SuperListEmployeItem item = findByOperationId(operationDto.getId());
                        if (item != null) {
                            processSubject.onNext(item);
                            Log.d("mahery-haja", "processing " + item.getItemId());
                        }
                    }
                });

        commandInvoker
                .onErrorObservable()
                .subscribe(new Consumer<OperationDto>() {
                    @Override
                    public void accept(OperationDto operationDto) throws Exception {
                        Log.d("mahery-haja", "error " + operationDto.getId());
                        SuperListEmployeItem item = findByOperationId(operationDto.getId());
                        if (item != null) {
                            errorProcessSubject.onNext(item);
                        }

                    }
                });

    }

    private SuperListEmployeItem findByOperationId(Long id) {

        int itemId = (id.intValue()) * -1;

        if (itemMap.containsKey(itemId))
            return itemMap.get(itemId);
        return null;
    }

    @android.support.annotation.NonNull
    private ListEmployeItemTemp createListEmployeItemTemp(OperationDto<EmployeDto> employeDtoOperationDto) {
        ListEmployeItemTemp listEmployeItemTemp = fromOperationDto(employeDtoOperationDto);
        EmployeDto employeDto = employeDtoOperationDto.getData();
        int id = listEmployeItemTemp.getItemId();

        if (employeDto.getId() != null) {
            //update or delete
            Log.d("mahery-haja", "update or delete");
            int employeId = employeDto.getId().intValue();


            // element deja present
            if (itemMap.containsKey(employeId)) {
                // replacement
                replaceSubject.onNext(new ItemReplacement(itemMap.get(employeId), listEmployeItemTemp));
                itemMap.remove(employeId);


            } else {
                // simple ajout
                addSubject.onNext(listEmployeItemTemp);
            }

            Log.d("mahery-haja", "putting id" + id);
            itemMap.put(id, listEmployeItemTemp);
        } else {
            // create
            itemMap.put(id, listEmployeItemTemp);
            addSubject.onNext(listEmployeItemTemp);

        }

        return listEmployeItemTemp;
    }

    public ListEmployeItemTemp fromOperationDto(OperationDto<EmployeDto> employeDtoOperationDto) {
        ListEmployeItemTemp listEmployeItemTemp = new ListEmployeItemTemp(employeDtoOperationDto.getData(), employeDtoOperationDto.getOperationName());
        int id = (int) employeDtoOperationDto.getId();
        id = id * -1;
        listEmployeItemTemp.setItemId(id);
        return listEmployeItemTemp;
    }

    @android.support.annotation.NonNull
    private ListEmployeItem createListEmployeItem(EmployeDto employeDto) {
        ListEmployeItem listEmployeItem = new ListEmployeItem(employeDto);


        /***
         * A mettre dans une factory
         */


        int id = employeDto.getId().intValue();
        listEmployeItem.setItemId(employeDto.getId().intValue());
        if (!getEmployeIdSet().contains(employeDto.getId())) {
            itemMap.put(id, listEmployeItem);
            itemMap.put(id, listEmployeItem);
            addSubject.onNext(listEmployeItem);
        }
        return listEmployeItem;
    }

    @Override
    public Observable<SuperListEmployeItem> getActualListObservable() {
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
    public Observable<ItemReplacement> onReplaceObservable() {
        return replaceSubject;
    }

    @Override
    public EmployeDto findByitemId(int id) {
        return itemMap.get(id).getEmployeDto();
    }

    private HashSet<Long> getEmployeIdSet() {
        HashSet<Long> longHashSet = new HashSet<>();

        for (SuperListEmployeItem superListEmployeItem : itemMap.values()) {
            try {
                longHashSet.add(superListEmployeItem.getEmployeDto().getId());
            } catch (NullPointerException e) {

            }
        }

        return longHashSet;
    }

    @Override
    public String findOperationNameById(int id) {
        if (id < 0) {

            ListEmployeItemTemp superListEmployeItem = (ListEmployeItemTemp) itemMap.get(id);
            return superListEmployeItem.getOperationName();


        } else {
            return null;
        }
    }

    @Override
    public void requestDeleteItemById(int itemId) {
        // simple verification
        if (itemMap.containsKey(itemId)) {

            if (itemId > 0) {
                // simple employe
            } else {
                // delete of operation
                operationStackSynchroSA.deleteOperationById(itemId * -1);
            }

        }
    }

    @Override
    public List<SuperListEmployeItem> getActualList() {
        return new ArrayList<>(itemMap.values());
    }


    @Override
    public Observable<SuperListEmployeItem> onProcessObservable() {
        return processSubject;
    }

    @Override
    public Observable<SuperListEmployeItem> onErrorObservable() {
        return errorProcessSubject;
    }
}
