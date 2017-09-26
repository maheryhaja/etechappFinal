package mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSAImpl;

@EActivity(R.layout.activity_create_employe)
public class CreateEmployeActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmenCreateEmploye)
    CreateEmployeFragment createEmployeFragment;




}
