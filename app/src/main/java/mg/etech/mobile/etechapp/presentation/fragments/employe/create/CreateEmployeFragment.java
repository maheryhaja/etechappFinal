
package mg.etech.mobile.etechapp.presentation.fragments.employe.create;


import android.support.v4.app.Fragment;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragmentWithValidator;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_create_employe)
public class CreateEmployeFragment extends AbstractFragmentWithValidator {

    @ViewById(R.id.spinner_poleDto)
    Spinner spinnerPoleDto;

    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    private PoleSpinnerAdapter spinnerAdapter;

    public CreateEmployeFragment() {
        // Required empty public constructor
    }

    @Override
    public void validationSucceeded() {

    }

    @AfterViews
    public void initAfterView() {
        spinnerAdapter = new PoleSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, poleSA.findAll());
        spinnerPoleDto.setAdapter(spinnerAdapter);

    }
}
