package mg.etech.mobile.etechapp.presentation.activities.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressImageButton;
import mg.etech.mobile.etechapp.R;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.progressBtnLogin)
    CircularProgressButton btnLogin;

    @AfterViews
    public void initAfterViews() {
    }

    @Click(R.id.progressBtnLogin)
    void onLoginClicked(){
        btnLogin.startAnimation();
    }
}
