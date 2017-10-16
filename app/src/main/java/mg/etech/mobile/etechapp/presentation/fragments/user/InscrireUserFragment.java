package mg.etech.mobile.etechapp.presentation.fragments.user;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Callable;

import javax.net.ssl.SSLException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.customsnackbuilder.CustomSnackBarBuilder;
import mg.etech.mobile.etechapp.commun.exception.HandleErrors;
import mg.etech.mobile.etechapp.commun.exception.user.CreateUserFailedException;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Email;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Required;
import mg.etech.mobile.etechapp.donnee.dto.UserDto;
import mg.etech.mobile.etechapp.presentation.customviews.Base64PhotoPicker;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragmentWithValidator;
import mg.etech.mobile.etechapp.service.applicatif.UserSA;
import mg.etech.mobile.etechapp.service.applicatif.UserSAImpl;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_inscrire_user)
public class InscrireUserFragment extends AbstractFragmentWithValidator implements HandleErrors {


    @ViewById(R.id.layoutInscrireRoot)
    LinearLayout rootLayout;

    @Required(order = 1, messageResId = R.string.required_mail)
    @Email(order = 1, messageResId = R.string.email_incorrect)
    @ViewById(R.id.edtCreateUserIdentifaint)
    EditText edtLogin;

    @Required(order = 2, messageResId = R.string.required_nom)
    @ViewById(R.id.edtCreateUserNom)
    EditText edtNom;

    @Required(order = 3, messageResId = R.string.required_prenom)
    @ViewById(R.id.edtCreateUserPrenom)
    EditText edtPrenom;

    @Required(order=4, messageResId = R.string.required_password)
    @ViewById(R.id.edtCreateUserPassword)
    EditText edtPassword;

    @ViewById(R.id.b64Picker)
    Base64PhotoPicker photoPicker;

    @Bean(UserSAImpl.class)
    UserSA userSA;


    @ViewById(R.id.btnValiderInscription)
    CircularProgressButton btnValiderInscription;


    @AfterViews
    public void initAfterViews() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public InscrireUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void validationSucceeded() {
        if (photoPicker.isSet()) {

            UserDto userDto = new UserDto();
            userDto.setFirstName(edtPrenom.getText().toString());
            userDto.setLastname(edtNom.getText().toString());
            userDto.setPassword(edtPassword.getText().toString());
            userDto.setLogin(edtLogin.getText().toString());

            //a revoir
            userDto.setPhoto(photoPicker.getValue());
            inscrireUser(userDto);

        } else {
            onPhotoPickerNotSet();
        }
    }

    private void onPhotoPickerNotSet() {
        Toast.makeText(getContext(), "Veuillez choisir une photo", Toast.LENGTH_SHORT).show();

    }

    @Click(R.id.btnValiderInscription)
    public void onValiderInscriptionClicked() {
        validator.validate();

    }

    public void inscrireUser(final UserDto userDto) {

        btnValiderInscription.startAnimation();

        Observable
                .fromCallable(new Callable<UserDto>() {

                    @Override
                    public UserDto call() throws Exception {
                        UserDto user = userSA.createUser(userDto);
                        Log.d("mahery-haja","creation success test "+user.getLogin());
                        return user;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserDto>() {
                               @Override
                               public void accept(UserDto userDto) throws Exception {
                                   onCreateSuccess(userDto);


                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //on errors
                                handleError(throwable);
                                btnValiderInscription.revertAnimation();
                            }
                        }

                );
    }

    @Background(delay = 1000)
    void gotoLoginActivity() {
        ((Activity)getContext()).finish();
    }

    private void onCreateSuccess(UserDto userDto) {
        Log.d("mahery-haja", userDto.getLastname());
        btnValiderInscription.doneLoadingAnimation(getResources().getColor(R.color.etechGreen), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_checked));
        gotoLoginActivity();
    }


    @Override
    public void handleError(Throwable error) {

        if (error instanceof CreateUserFailedException) {

            afficherErreur("La creation d'utilisateur a echoué, veuillez reessayer ultérieurement");

        } else if (error instanceof SSLException) {
            afficherErreur("Probleme de connexion, veuillez reessayer");
        } else {


            afficherErreur("Not implemented error");
            error.printStackTrace();
        }
    }

    protected void afficherErreur(String message) {
        CustomSnackBarBuilder
                .make(rootLayout, message, Snackbar.LENGTH_LONG)
                .withColor(R.color.etechWhiteGray)
                .createSnackBar()
                .show();
    }
}
