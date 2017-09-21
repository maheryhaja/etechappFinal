package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeItemTemp extends SuperListEmployeItem<ListEmployeTempViewHolder> {
    public ListEmployeItemTemp(EmployeDto employeDto) {
        super(employeDto);
    }

    @Override
    public ListEmployeTempViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.itemview_liste_employe_temp, parent, false);

        return new ListEmployeTempViewHolder(linearLayout, adapter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.itemview_liste_employe_temp;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ListEmployeTempViewHolder holder, int position, List payloads) {
        super.bindViewHolder(adapter, holder, position, payloads);
    }
}
