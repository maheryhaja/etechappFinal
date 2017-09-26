package mg.etech.mobile.etechapp.presentation.fragments.employe.update;

import org.androidannotations.annotations.EFragment;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;

/**
 * Created by mahery.haja on 26/09/2017.
 */

@EFragment(R.layout.fragment_create_employe)
public class UpdateEmployeFragment extends CreateEmployeFragment {


    public void initEmployeDto(EmployeDto emp) {
        //set all field in the fragment
    }
}
