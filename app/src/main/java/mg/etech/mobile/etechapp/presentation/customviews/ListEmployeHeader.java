package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import mg.etech.mobile.etechapp.R;

/**
 * Created by mahery.haja on 18/10/2017.
 */
@EViewGroup(R.layout.viewgroup_list_employe_header)
public class ListEmployeHeader extends LinearLayout {

    @ViewById(R.id.txtHeaderTitre)
    TextView txtTitre;

    public ListEmployeHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public void setTitre(String titre) {
        txtTitre.setText(titre);
    }
}
