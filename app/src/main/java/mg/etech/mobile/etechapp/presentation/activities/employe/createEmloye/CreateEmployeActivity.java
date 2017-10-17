package mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;

@EActivity(R.layout.activity_create_employe)
public class CreateEmployeActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmenCreateEmploye)
    CreateEmployeFragment createEmployeFragment;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }


}
