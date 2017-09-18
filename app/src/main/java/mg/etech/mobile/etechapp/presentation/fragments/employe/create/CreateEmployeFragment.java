
package mg.etech.mobile.etechapp.presentation.fragments.employe.create;


import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.concurrent.Callable;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.exception.HandleErrors;
import mg.etech.mobile.etechapp.commun.utils.date.datepicker.SimpleDatePicker;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Required;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;
import mg.etech.mobile.etechapp.presentation.customviews.Base64PhotoPicker;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragmentWithValidator;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSA;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSAImpl;


@EFragment(R.layout.fragment_create_employe)
public class CreateEmployeFragment extends AbstractFragmentWithValidator implements HandleErrors {

    @Required(order = 1, messageResId = R.string.required_create_employe_nom)
    @ViewById(R.id.edtCreateEmployeNom)
    EditText edtNom;

    @Required(order = 2, messageResId = R.string.required_create_employe_prenom)
    @ViewById(R.id.edtCreateEmployePrenom)
    EditText edtPrenom;

    @ViewById(R.id.edtCreateEmployeAllias)
    EditText edtAllias;

    @Required(order = 3, messageResId = R.string.required_create_employe_matricule)
    @ViewById(R.id.edtCreateEmployeMatricule)
    EditText edtMatricule;

    @Required(order = 4, messageResId = R.string.required_create_employe_birthdate)
    @ViewById(R.id.edtCreateEmployeBirthDate)
    SimpleDatePicker edtBirthDate;

    @Required(order = 5, messageResId = R.string.required_create_employe_hiringdate)
    @ViewById(R.id.edtCreateEmployeHiringDate)
    SimpleDatePicker edtHiringDate;

    @Required(order = 6, messageResId = R.string.required_mail)
    @ViewById(R.id.edtCreateEmployeMail)
    EditText edtMail;

    @ViewById(R.id.spinner_poleDto)
    Spinner spinnerPoleDto;

    @ViewById(R.id.btnCreaterEmploye)
    CircularProgressButton btnCreateEmploye;

    @ViewById(R.id.b64CreateEmployePicker)
    Base64PhotoPicker base64PhotoPicker;

    private List<PoleDto> poleDtos;
    private List<PosteDto> posteDtos;

    private boolean isMale = true;



    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    @Bean(PosteSAImpl.class)
    PosteSA posteSA;

    @Bean(BackSynchronizerSAImpl.class)
    BackSynchronizerSA backSynchronizerSA;

    private PoleSpinnerAdapter spinnerAdapter;

    public CreateEmployeFragment() {
        // Required empty public constructor
    }

    @Override
    public void validationSucceeded() {
        String allias = edtAllias.getText().toString();
        if (allias.isEmpty() || allias.equals("") || allias.equals("null") || allias == null) {
            edtAllias.setText(edtPrenom.getText());
        }

        if (!base64PhotoPicker.isSet()) {
            onPhotoPickerNotSet();
        } else {
            // start animation
            btnCreateEmploye.startAnimation();
            getEmployeDtofromForm();
        }
    }

    private void onPhotoPickerNotSet() {
        Toast.makeText(pActivity, "Veuillez choisir une photo", Toast.LENGTH_SHORT).show();
    }

    private void getEmployeDtofromForm() {


        EmployeDto employeDto = new EmployeDto();

        // set employeDto value
        employeDto.setLastName(edtNom.getText().toString());
        employeDto.setFirstName(edtPrenom.getText().toString());
        employeDto.setAlias(edtAllias.getText().toString());
        employeDto.setMatricule(Long.valueOf(edtMatricule.getText().toString()));
        employeDto.setHiringDate(edtHiringDate.getDate());
        employeDto.setBirthDate(edtBirthDate.getDate());
        employeDto.setMale(isMale);
        employeDto.setMail(edtMail.getText().toString());
        employeDto.setPhoto(base64PhotoPicker.getValue());
        employeDto.setPole(poleDtos.get(spinnerPoleDto.getSelectedItemPosition()));

        createEmploye(employeDto);
    }

    private void createEmploye(final EmployeDto employeDto) {
        Observable
                .fromCallable(new Callable<EmployeDto>() {
                    @Override
                    public EmployeDto call() throws Exception {
                        return backSynchronizerSA.createEmploye(employeDto);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeDto>() {
                               @Override
                               public void accept(EmployeDto employeDto) throws Exception {
                                   // on success
                                   Toast.makeText(pActivity, "success " + employeDto.getId(), Toast.LENGTH_SHORT).show();
                                   onCreateEmplyeSuccess();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                handleError(throwable);
                                btnCreateEmploye.revertAnimation();
                            }
                        }

                );
    }

    private void onCreateEmplyeSuccess() {
        btnCreateEmploye.doneLoadingAnimation(getResources().getColor(R.color.etechGreen), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_checked));
        pActivity.finish();
    }

    @AfterViews
    public void initAfterView() {
        poleDtos = poleSA.findAll();
        posteDtos = posteSA.findAll();
        spinnerAdapter = new PoleSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, poleDtos);
        spinnerPoleDto.setAdapter(spinnerAdapter);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Click(R.id.rBtnCreateEmployeHomme)
    void hommeClicked() {
        isMale = true;
    }

    @Click(R.id.rBtnCreateEmployeFemme)
    void femmeClicked() {
        isMale = false;
    }

    @Click(R.id.btnCreaterEmploye)
    void onCreateEmployeClicked() {
        validator.validate();
    }

    @Override
    public void handleError(Throwable error) {
        Toast.makeText(pActivity, "Une erreur vient de survenir", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.btnCreateEmployeAddPoste)
    void onAddPosteClicked() {
        AddPosteDialog addPosteDialog = new AddPosteDialog(getContext(), getLayoutInflater(), posteDtos);
        addPosteDialog.show();
    }

}
