package mg.etech.mobile.etechapp.presentation.fragments.employe.list.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.R;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class ContextMenuDialogImpl implements ContextMenuDialog {

    private PublishSubject<Boolean> updateSubject = PublishSubject.create();
    private PublishSubject<Boolean> deleteSubject = PublishSubject.create();
    private Activity activity;
    private Dialog dialog;
    private LayoutInflater inflater;
    private LinearLayout modifierLayout;
    private LinearLayout supprimerLayout;
    private LinearLayout rootViewLayout;


    public ContextMenuDialogImpl(Activity activity) {
        this.activity = activity;
        dialog = create();
        modifierLayout = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextModifier);
        supprimerLayout = (LinearLayout) rootViewLayout.findViewById(R.id.layoutContextSupprimer);

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
}
