package mg.etech.mobile.etechapp.presentation.activities.inscription;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.user.InscrireUserFragment;

@EActivity(R.layout.activity_inscription)
public class InscriptionActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmentInscrireUser)
    InscrireUserFragment inscrireUserFragment;


    @AfterViews
    void initAfterViews() {

    }

}
