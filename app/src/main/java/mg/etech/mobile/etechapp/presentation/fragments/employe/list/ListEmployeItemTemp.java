package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeItemTemp extends SuperListEmployeItem<ListEmployeTempViewHolder> {

    protected String operationName= OperationType.CREATE;

    public ListEmployeItemTemp(EmployeDto employeDto) {
        super(employeDto);
    }

    public ListEmployeItemTemp(EmployeDto employeDto, String operationName) {
        super(employeDto);
        this.operationName = operationName;
    }

    @Override
    public ListEmployeTempViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.itemview_liste_employe_temp, parent, false);

        return new ListEmployeTempViewHolder(linearLayout, adapter, operationName);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.itemview_liste_employe_temp;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ListEmployeTempViewHolder holder, int position, List payloads) {
        super.bindViewHolder(adapter, holder, position, payloads);
        Log.d("mahery-haja", "try to bind " + operationName);
        holder.setOperationImage(operationName);
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
