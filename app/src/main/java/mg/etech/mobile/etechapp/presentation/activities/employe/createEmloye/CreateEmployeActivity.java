package mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;

@EActivity(R.layout.activity_create_employe)
public class CreateEmployeActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmenCreateEmploye)
    CreateEmployeFragment createEmployeFragment;

    @AfterViews
    void initAfterViews() {

    }


}
