package mg.etech.mobile.etechapp.commun.dialog.confirmation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.dialog.BaseDialog;

/**
 * Created by mahery.haja on 19/10/2017.
 */

// sert de base pour toutes les dialog de confirmation
public class ConfirmationDialog extends BaseDialog {


    public interface AfterConfirmListener {
        public void run();
    }


    private AfterConfirmListener afterConfirmListener = new AfterConfirmListener() {
        @Override
        public void run() {

        }
    };

    private String confirmationString = "";

    // Constructeur
    public ConfirmationDialog(Context ctx, LayoutInflater inf, String confirmation) {
        super(ctx, inf);
        confirmationString = confirmation;
        if (getView() != null) {
            content.addView(getView());
        }
    }


    @Override
    protected boolean onValiderClicked() {
        afterConfirmListener.run();
        return true;
    }

    @Override
    protected View getView() {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.dialog_confirmation, null);
        TextView txtConfirmation = (TextView) linearLayout.findViewById(R.id.txtConfirmationText);
        txtConfirmation.setText(confirmationString);
        return linearLayout;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    public AfterConfirmListener getAfterConfirmListener() {
        return afterConfirmListener;
    }

    public void setAfterConfirmListener(AfterConfirmListener afterConfirmListener) {
        this.afterConfirmListener = afterConfirmListener;
    }


}
