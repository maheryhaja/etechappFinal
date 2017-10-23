package mg.etech.mobile.etechapp.presentation.activities.error;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.activities.splash.SplashActivity_;

@EActivity(R.layout.activity_error)
public class ErrorActivity extends AppCompatActivity {

    @Click(R.id.btnErrorReessayer)
    public void reessayer() {
        SplashActivity_.intent(this).start();
        finish();

    }
}
