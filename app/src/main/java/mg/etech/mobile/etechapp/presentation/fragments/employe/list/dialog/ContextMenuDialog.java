package mg.etech.mobile.etechapp.presentation.fragments.employe.list.dialog;

import io.reactivex.Observable;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public interface ContextMenuDialog {
    void show();

    void dissmiss();


    Observable<Boolean> onUpdateSelected();

    Observable<Boolean> onDeleteSelected();

    Observable<Boolean> onRevertSelected();

}
