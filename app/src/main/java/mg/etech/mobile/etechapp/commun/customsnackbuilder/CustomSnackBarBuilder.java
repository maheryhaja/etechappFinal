package mg.etech.mobile.etechapp.commun.customsnackbuilder;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mahery.haja on 16/10/2017.
 */

public class CustomSnackBarBuilder {

    private Snackbar snackbar;

    public CustomSnackBarBuilder(View parent, String charSequence, int duration) {
        snackbar = Snackbar.make(parent, charSequence, duration);
    }

    private void setColor(int resColorId) {
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(view.getResources().getColor(resColorId));
    }

    public static CustomSnackBarBuilder make(View view, String texte, int duration) {
        return new CustomSnackBarBuilder(view, texte, duration);
    }

    public CustomSnackBarBuilder withColor(int resColorId) {
        setColor(resColorId);
        return this;
    }

    public Snackbar createSnackBar() {
        return snackbar;
    }

}
