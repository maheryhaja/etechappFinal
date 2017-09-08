package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.view.View;
import android.widget.TextView;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.viewholders.FlexibleViewHolder;
import mg.etech.mobile.etechapp.R;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public class ListEmployeViewHolder extends FlexibleViewHolder {

    private TextView titre;

    public ListEmployeViewHolder(View view, FlexibleAdapter adapter) {
        super(view, adapter);
        titre = (TextView) view.findViewById(R.id.txtItemNom);
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }
}
