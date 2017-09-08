package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public class ListEmployeItem extends AbstractFlexibleItem<ListEmployeViewHolder> {

    private EmployeDto employeDto;

    public ListEmployeItem(EmployeDto employeDto) {
        this.employeDto = employeDto;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof ListEmployeItem) {
            return ((ListEmployeItem) o).getEmployeDto().getId() == employeDto.getId();
        }
        return false;
    }

    public EmployeDto getEmployeDto() {
        return employeDto;
    }

    public void setEmployeDto(EmployeDto employeDto) {
        this.employeDto = employeDto;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.itemview_list_employe;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ListEmployeViewHolder holder, int position, List payloads) {
        holder.setTitre(employeDto.getFirstName() + " " + employeDto.getLastName());
    }

    @Override
    public ListEmployeViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.itemview_list_employe, null);
        return new ListEmployeViewHolder(linearLayout, adapter);
    }
}
