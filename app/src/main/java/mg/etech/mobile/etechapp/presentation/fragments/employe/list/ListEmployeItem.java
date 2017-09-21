package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeItem extends SuperListEmployeItem<ListEmployeViewHolder> {
    public ListEmployeItem(EmployeDto employeDto) {
        super(employeDto);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.itemview_liste_employe;
    }

    @Override
    public ListEmployeViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.itemview_liste_employe, parent, false);
        return new ListEmployeViewHolder(linearLayout, adapter);
    }
}
