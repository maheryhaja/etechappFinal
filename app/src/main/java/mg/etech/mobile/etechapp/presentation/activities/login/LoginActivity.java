package mg.etech.mobile.etechapp.presentation.activities.login;

import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Callable;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Required;
import mg.etech.mobile.etechapp.contrainte.factory.dto.user.UserDtoFromWSFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.presentation.activities.AbstractActivity;
import mg.etech.mobile.etechapp.presentation.activities.inscription.InscriptionActivity_;
import mg.etech.mobile.etechapp.presentation.activities.main.MainActivity_;
import mg.etech.mobile.etechapp.service.applicatif.preferences.PreferenceSA;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.UserSA;
import mg.etech.mobile.etechapp.service.applicatif.UserSAImpl;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AbstractActivity {

    @ViewById(R.id.progressBtnLogin)
    CircularProgressButton btnLogin;

    @ViewById(R.id.btnSinscrire)
    CircularProgressButton btnSinscrire;

    @ViewById(R.id.layoutLoginRoot)
    RelativeLayout layoutLoginRoot;


    @Bean(PreferenceSAImpl.class)
    PreferenceSA preferenceSA;

    @Required(order = 1, messageResId = R.string.required_mail)
    @ViewById(R.id.edtLogin)
    EditText edtLogin;

    @Required(order = 2, messageResId = R.string.required_password)
    @ViewById(R.id.edtPassword)
    EditText edtPassword;

    @Bean
    UserDtoFromWSFactoryImpl userDtoFromWSFactory;

    @Bean(UserSAImpl.class)
    UserSA userSA;

    @AfterViews
    public void initAfterViews() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Click(R.id.progressBtnLogin)
    void onLoginClicked(){
        validator.validate();

    }

    @Click(R.id.btnSinscrire)
    void onSinscrireClicked() {
        InscriptionActivity_.intent(this).start();
    }

    @Override
    public void validationSucceeded() {
        btnLogin.startAnimation();

        Observable
                .fromCallable(new Callable<UserDto>() {
                    @Override
                    public UserDto call() throws Exception {
                        // userSA call
                        UserDto resUserDto = userSA.logIn(edtLogin.getText().toString(), edtPassword.getText().toString());
                        return resUserDto;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserDto>() {
                               @Override
                               public void accept(UserDto userDto) throws Exception {
                                   Log.d("mahery-haja", userDto.getFirstName());
                                   onLoginSuccessFull(userDto);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // on Some rerrors
                                handleLoginErrors(throwable);
                            }
                        }
                );



    }

    private void handleLoginErrors(Throwable throwable) {

        btnLogin.revertAnimation();
        // handle error

        if (throwable instanceof LoginFailedException) {
            Snackbar
                    .make(layoutLoginRoot, "Login failde sorry", Snackbar.LENGTH_LONG)
                    .show();
        } else {

        }

    }

    private void onLoginSuccessFull(UserDto userDto) {
        //handle login successfull
        preferenceSA.setUserConnected(true);
        btnSinscrire.doneLoadingAnimation(getResources().getColor(R.color.etechGreen), BitmapFactory.decodeResource(getResources(), R.drawable.ic_checked));
        goToMainActivity();


    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Background(delay = 2000)
    void goToMainActivity() {
        MainActivity_.intent(this).start();
        finish();
    }
}
