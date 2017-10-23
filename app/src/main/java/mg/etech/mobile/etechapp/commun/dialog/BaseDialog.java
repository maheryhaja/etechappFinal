package mg.etech.mobile.etechapp.commun.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import mg.etech.mobile.etechapp.R;

/**
 * Created by maheryHaja on 9/18/2017.
 */

public abstract class BaseDialog {

    protected AlertDialog.Builder builder;
    //    protected Context context;
    protected LayoutInflater inflater;
    protected LinearLayout baseLayout;
    protected TextView txtDialogTitle;
    protected Button btnValider;
    protected Button btnAnnuler;
    protected Dialog dialog;
    protected LinearLayout content;

    public BaseDialog(Context ctx, LayoutInflater inf) {
//        this.context = ctx;
        builder = new AlertDialog.Builder(ctx);
        inflater = inf;
        initViews();
    }

    private void initViews() {
        baseLayout = (LinearLayout) inflater.inflate(R.layout.dialog_base, null);
       content = (LinearLayout) baseLayout.findViewById(R.id.dialogContent);

        txtDialogTitle = (TextView) baseLayout.findViewById(R.id.txtDialogTitle);

        if (getTitle() == null) {
            txtDialogTitle.setVisibility(View.GONE);
        } else {
        txtDialogTitle.setText(getTitle());
        }

        btnAnnuler = (Button) baseLayout.findViewById(R.id.btnDialogAnnuler);
        btnValider = (Button) baseLayout.findViewById(R.id.btnDialogValider);


        dialog = builder
                .setView(baseLayout)
                .create();

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onValiderClicked()) {
                    dialog.dismiss();
                }
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAnnulerClicked();
            }
        });

    }

    protected void onAnnulerClicked() {
        dialog.dismiss();
    }

    protected abstract boolean onValiderClicked();

    public void show() {

                dialog.show();
    }

    protected abstract View getView();

    protected abstract String getTitle();

}
