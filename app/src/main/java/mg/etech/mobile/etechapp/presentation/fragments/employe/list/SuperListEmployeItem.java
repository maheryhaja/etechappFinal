package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.util.Log;
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
public class SuperListEmployeItem<T extends ListEmployeViewHolder> extends AbstractFlexibleItem<T>

{


    protected EmployeDto employeDto;
    protected int itemId;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public SuperListEmployeItem(EmployeDto employeDto) {
        this.employeDto = employeDto;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof SuperListEmployeItem) {
            return ((SuperListEmployeItem) o).getItemId() == this.getItemId();
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
        return R.layout.itemview_list_employe_inside;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, T holder, int position, List payloads) {
        holder.setTitre(employeDto.getFirstName() + " " + employeDto.getLastName());

        try {
            holder.setMatricule(employeDto.getMatricule());

        } catch (NullPointerException e) {
            Log.d("mahery-haja", "pas de matricule");

        }

        if (employeDto.getEncodedPhoto() != null) {
            holder.setEncodedPhoto(employeDto.getEncodedPhoto());
        } else if (employeDto.getPhoto() != null) {
            holder.setPhoto(employeDto.getPhoto());
        } else {
            holder.setDefault(employeDto.isMale());
        }
    }

    @Override
    public T createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.itemview_list_employe_inside, parent, false);
        return (T) new ListEmployeViewHolder(linearLayout, adapter);
    }


}
