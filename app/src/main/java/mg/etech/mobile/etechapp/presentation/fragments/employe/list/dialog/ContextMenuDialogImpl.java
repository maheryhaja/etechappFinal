package mg.etech.mobile.etechapp.presentation.fragments.employe.list.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItemTemp;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;

import static android.view.View.GONE;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class ContextMenuDialogImpl implements ContextMenuDialog {

    private PublishSubject<Boolean> updateSubject = PublishSubject.create();
    private PublishSubject<Boolean> deleteSubject = PublishSubject.create();
    private PublishSubject<Boolean> revertSubject = PublishSubject.create();

    private Activity activity;
    private Dialog dialog;
    private LayoutInflater inflater;
    private LinearLayout modifierLayout;
    private LinearLayout supprimerLayout;
    private LinearLayout revertLayout;
    private LinearLayout rootViewLayout;
    private SuperListEmployeItem item;

    public ContextMenuDialogImpl(Activity activity, SuperListEmployeItem item) {
        this.activity = activity;
        this.item = item;
        dialog = create();
        modifierLayout = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextModifier);
        supprimerLayout = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextSupprimer);
        revertLayout = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextRevert);

        modifierLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubject.onNext(true);
                dialog.dismiss();
            }
        });

        supprimerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSubject.onNext(true);
                dialog.dismiss();
            }
        });

        revertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revertSubject.onNext(true);
                dialog.dismiss();
            }
        });


    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Dialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        rootViewLayout = (LinearLayout) inflater.inflate(R.layout.dialog_context_menu, null);

        if (item instanceof ListEmployeItemTemp) {
            // faire apparaitre le bouton revert opration apparaitre bouton revert

            ListEmployeItemTemp listEmployeItemTemp = (ListEmployeItemTemp) item;
            Log.d("mahery-haja", "instance of temp");

            LinearLayout delete = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextSupprimer);
            delete.setVisibility(GONE);

            LinearLayout revert = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextRevert);
            revert.setVisibility(View.VISIBLE);

            if (listEmployeItemTemp.getOperationName().equals(OperationType.DELETE)) {
                LinearLayout update = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextModifier);
                update.setVisibility(GONE);
            }

        }

        builder.setView(rootViewLayout);
        return builder.create();
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public void dissmiss() {
        dialog.dismiss();
    }

    @Override
    public Observable<Boolean> onUpdateSelected() {
        return this.updateSubject;
    }

    @Override
    public Observable<Boolean> onDeleteSelected() {
        return this.deleteSubject;
    }

    @Override
    public Observable<Boolean> onRevertSelected() {
        return revertSubject;
    }
}
