package mg.etech.mobile.etechapp.service.applicatif.synchro.central;


import io.reactivex.Observable;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;

/**
 * Created by mahery.haja on 22/09/2017.
 */
// se charge de view model observable pour l'affichage :)
// se charge aussi de creer les listItem pour l'affichage
public interface CentralEmployeSynchroSA {


    void initialize();

    EmployeDto findByitemId(int id);

    Observable<SuperListEmployeItem> getActualList();

    Observable<SuperListEmployeItem> onAddObservable();

    Observable<SuperListEmployeItem> onDeleteObservable();

    Observable<SuperListEmployeItem> onUpdateObservable();

    Observable<ItemReplacement> onReplaceObservable();

    void requestDeleteItemById(int itemId);

    String findOperationNameById(int id);
}
