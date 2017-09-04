package mg.etech.mobile.etechapp.presentation.fragments.user;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.exception.HandleErrors;
import mg.etech.mobile.etechapp.commun.exception.user.CreateUserFailedException;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
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

    @Required(order = 1, messageResId = R.string.required_mail)
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

        UserDto userDto = new UserDto();
        userDto.setFirstName(edtPrenom.getText().toString());
        userDto.setLastname(edtNom.getText().toString());
        userDto.setPassword(edtPassword.getText().toString());
        userDto.setLogin(edtLogin.getText().toString());

        //a revoir
        userDto.setPhoto(photoPicker.getValue());
        inscrireUser(userDto);

    }

    public void inscrireUser(final UserDto userDto) {

        Observable
                .just(userDto)
                .subscribeOn(Schedulers.io())
                .map(new Function<UserDto, UserDto>() {
                    @Override
                    public UserDto apply(@NonNull UserDto userDto) throws Exception {
                        Log.d("mahery-haja",userDto.getPhoto());
                        return userSA.createUser(userDto);
                    }
                })
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
                            }
                        }

                );
    }

    private void onCreateSuccess(UserDto userDto) {
        Log.d("mahery-haja", userDto.getLastname());
    }


    @Override
    public void handleError(Throwable error) {

        if (error instanceof CreateUserFailedException) {

            Log.d("mahery-haja","Create User Failed");

        } else {
            Log.d("mahery-haja","Another exception");
            error.printStackTrace();
        }
    }
}
