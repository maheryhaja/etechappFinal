package mg.etech.mobile.etechapp.presentation.fragments.employe.list;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public class ListEmployeItem extends SuperListEmployeItem<ListEmployeViewHolder> {
    public ListEmployeItem(EmployeDto employeDto) {
        super(employeDto);
    }
}
