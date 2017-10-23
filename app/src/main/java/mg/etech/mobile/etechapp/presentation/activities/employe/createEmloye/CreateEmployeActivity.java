package mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment_;

@EActivity(R.layout.activity_create_employe)
public class CreateEmployeActivity extends AppCompatActivity {

    private CreateEmployeFragment_ createEmployeFragment;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(createEmployeFragment)
                .commit();
        createEmployeFragment = null;
        finish();
    }

    @AfterViews
    void initAfterViews() {
        createEmployeFragment = new CreateEmployeFragment_();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.placeFragmentCreateEmploye, createEmployeFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
