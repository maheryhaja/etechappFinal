package mg.etech.mobile.etechapp.presentation.activities.login;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.exception.user.LoginFailedException;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Required;
import mg.etech.mobile.etechapp.contrainte.factory.dto.UserDtoFromWSFactoryImpl;
import mg.etech.mobile.etechapp.presentation.activities.AbstractActivity;
import mg.etech.mobile.etechapp.service.applicatif.UserSA;
import mg.etech.mobile.etechapp.service.applicatif.UserSAImpl;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AbstractActivity {

    @ViewById(R.id.progressBtnLogin)
    CircularProgressButton btnLogin;

    @ViewById(R.id.btnSinscrire)
    CircularProgressButton btnSinscrire;


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
    }

    private void setBtnSinscrireStat(boolean stat){
        GradientDrawable gradientDrawable = (GradientDrawable) btnSinscrire.getBackground();
        if (stat) {
            gradientDrawable.setColor(getResources().getColor(R.color.darkGray));
        } else {

        }
    }

    @Override
    public void validationSucceeded() {
        btnLogin.startAnimation();

//        Observable
//                .fromCallable(new Callable<UserDto>() {
//                    @Override
//                    public UserDto call() throws Exception {
//                        UserDto resUserDto = userSA.logIn(edtLogin.getText().toString(), edtPassword.getText().toString());
//                        return resUserDto;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<UserDto>() {
//                    @Override
//                    public void accept(UserDto userDto) throws Exception {
//                    Log.d("mahery-haja",userDto.getFirstName());
//                    }
//                });

        try {
            Log.d("mahery-haja", userSA.logIn(edtLogin.getText().toString(), edtPassword.getText().toString()).getFirstName());
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }

    }
}
