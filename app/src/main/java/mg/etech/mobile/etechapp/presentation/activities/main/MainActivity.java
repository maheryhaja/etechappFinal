package mg.etech.mobile.etechapp.presentation.activities.main;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.activities.login.LoginActivity_;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSA;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSAImpl;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean(PreferenceSAImpl.class)
    PreferenceSA preferenceSA;

    @Click(R.id.btnLogout)
    void logoutClicked() {
        preferenceSA.setUserConnected(false);
        LoginActivity_.intent(this).start();
        finish();
    }
}
