
package mg.etech.mobile.etechapp.presentation.fragments.employe.create;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipDeletedListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.constante.SimpleDate;
import mg.etech.mobile.etechapp.commun.exception.HandleErrors;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.commun.utils.date.SimpleDateUtils;
import mg.etech.mobile.etechapp.commun.utils.date.datepicker.SimpleDatePicker;
import mg.etech.mobile.etechapp.commun.utils.validator.Validator;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Email;
import mg.etech.mobile.etechapp.commun.utils.validator.annotation.Required;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;
import mg.etech.mobile.etechapp.presentation.customviews.Base64PhotoPicker;
import mg.etech.mobile.etechapp.presentation.customviews.ExpandableFormSection;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragmentWithValidator;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.CreateEmployeCommand;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory.OperationCommandFactory;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.factory.OperationCommandFactoryImpl;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSA;
import mg.etech.mobile.etechapp.service.applicatif.poste.PosteSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;


@EFragment(R.layout.fragment_create_employe)
public class CreateEmployeFragment extends AbstractFragmentWithValidator implements HandleErrors {

    @Required(order = 2, messageResId = R.string.required_create_employe_nom)
    @ViewById(R.id.edtCreateEmployeNom)
    protected EditText edtNom;

    @Required(order = 3, messageResId = R.string.required_create_employe_prenom)
    @ViewById(R.id.edtCreateEmployePrenom)
    protected EditText edtPrenom;

    @ViewById(R.id.edtCreateEmployeAllias)
    protected EditText edtAllias;

    @Required(order = 1, messageResId = R.string.required_create_employe_matricule)
    @ViewById(R.id.edtCreateEmployeMatricule)
    protected EditText edtMatricule;

    @Required(order = 4, messageResId = R.string.required_create_employe_birthdate)
    @ViewById(R.id.edtCreateEmployeBirthDate)
    protected SimpleDatePicker edtBirthDate;

    @Required(order = 5, messageResId = R.string.required_create_employe_hiringdate)
    @ViewById(R.id.edtCreateEmployeHiringDate)
    protected SimpleDatePicker edtHiringDate;

    @ViewById(R.id.rGroupMale)
    protected RadioGroup rGroupMale;

    @Required(order = 6, messageResId = R.string.required_mail)
    @Email(order = 6, messageResId = R.string.email_incorrect)
    @ViewById(R.id.edtCreateEmployeMail)
    protected EditText edtMail;

    @ViewById(R.id.spinner_poleDto)
    protected Spinner spinnerPoleDto;

    @ViewById(R.id.btnCreaterEmploye)
    protected CircularProgressButton btnCreateEmploye;

    @ViewById(R.id.b64CreateEmployePicker)
    protected Base64PhotoPicker base64PhotoPicker;

    @ViewById(R.id.chipFlexBoxLayout)
    protected FlexboxLayout chipFlexBoxLayout;

    @ViewById(R.id.expandableInfoPers)
    protected ExpandableFormSection expandableInfoPers;

    @ViewById(R.id.expandableInfoPro)
    protected ExpandableFormSection expandableInfoPro;

    @Bean(OperationCommandFactoryImpl.class)
    protected OperationCommandFactory operationCommandFactory;

    protected AddPosteDialog addPosteDialog;

    protected List<HistoryPosteDto> historyPosteDtos = new ArrayList<>();
    protected ChipCloud chipCloud;

    protected List<PoleDto> poleDtos;
    protected List<PosteDto> posteDtos;

    protected boolean isMale = true;



    @Bean(PoleSAImpl.class)
    protected PoleSA poleSA;

    @Bean(PosteSAImpl.class)
    protected PosteSA posteSA;

    @Bean(BackSynchronizerSAImpl.class)
    protected BackSynchronizerSA backSynchronizerSA;

    @Bean(OperationStackSynchroSAImpl.class)
    protected OperationStackSynchroSA operationStackSynchroSA;

    protected PoleSpinnerAdapter spinnerAdapter;

    final Calendar calendar = Calendar.getInstance();

    public CreateEmployeFragment() {
        // Required empty public constructor
    }


    @Override
    public void validationSucceeded() {
        String allias = edtAllias.getText().toString();
        if (allias.isEmpty() || allias.equals("") || allias.equals("null") || allias == null) {
            edtAllias.setText(edtPrenom.getText());
        }

//        if (!base64PhotoPicker.isSet()) {
//            onPhotoPickerNotSet();
//        }
        else {
            // start animation

            if (historyPosteDtos.size() == 0) {
                Toast.makeText(getContext(), "Veuillez choisir au moins un poste", Toast.LENGTH_LONG).show();
            } else {

                btnCreateEmploye.startAnimation();
                getEmployeDtofromForm();

            }
        }
    }

    protected void onPhotoPickerNotSet() {
        Toast.makeText(pActivity, "Veuillez choisir une photo", Toast.LENGTH_SHORT).show();
    }

    protected void getEmployeDtofromForm() {


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

        // mettre la photo prise dans encoded photo
        employeDto.setEncodedPhoto(base64PhotoPicker.getValue());
        employeDto.setPole(poleDtos.get(spinnerPoleDto.getSelectedItemPosition()));
        employeDto.setPostes(historyPosteDtos);

        if (base64PhotoPicker.isSet()) {
            employeDto.setEncodedPhoto(base64PhotoPicker.getValue());
        } else if (base64PhotoPicker.isUrlSet()) {
            employeDto.setPhoto(base64PhotoPicker.getUrlValue());
        }

        createEmploye(employeDto);
    }

    protected void createEmploye(final EmployeDto employeDto) {
        Observable
                .fromCallable(new Callable<EmployeDto>() {
                    @Override
                    public EmployeDto call() throws Exception {
                        return performOperation(employeDto);

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeDto>() {
                               @Override
                               public void accept(EmployeDto employeDto) throws Exception {
                                   // on success
                                   onCreateEmployeSuccess();
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

    protected EmployeDto performOperation(EmployeDto employeDto) {
        CreateEmployeCommand command;

        OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<EmployeDto>();
        employeDtoOperationDto.setOperationName(OperationType.CREATE);
        employeDtoOperationDto.setData(employeDto);
        employeDtoOperationDto.setClassName(EmployeDto.class.getName());
        operationStackSynchroSA.addOperation(employeDtoOperationDto);

        return employeDto;
    }

    protected void onCreateEmployeSuccess() {
        btnCreateEmploye.doneLoadingAnimation(getResources().getColor(R.color.etechGreen), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_checked));

//        pActivity.finish();


        Observable
                .timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        pActivity.finish();
                    }
                });
    }



    @AfterViews
    public void initAfterView() {
        poleDtos = poleSA.findAll();
        posteDtos = posteSA.findAll();
        spinnerAdapter = new PoleSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, poleDtos);
        spinnerPoleDto.setAdapter(spinnerAdapter);
        validator = new Validator(this);
        validator.setValidationListener(this);

        expandableInfoPers.setTitle(getString(R.string.info_pers_title));
        expandableInfoPro.setTitle(getString(R.string.info_pro_title));

        initializeDialog();

        initialiseChipCloud();

    }

    protected void initializeDialog() {
        addPosteDialog = new AddPosteDialog(getContext(), getLayoutInflater(null), posteDtos);

        addPosteDialog
                .getHistoryPosteDtoObservable()
                .subscribe(new Consumer<HistoryPosteDto>() {
                               @Override
                               public void accept(HistoryPosteDto historyPosteDto) throws Exception {
                                   // give a temporary negative id
                                   historyPosteDto.setId((long) ((historyPosteDtos.size() + 1) * -1));
                                   historyPosteDtos.add(historyPosteDto);
                                   chipCloud.addChip(chipFromHistoryDto(historyPosteDto));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("mahery-haja", "erreur observable");
                                throwable.printStackTrace();
                            }
                        }
                );
    }

    @NonNull
    protected String chipFromHistoryDto(HistoryPosteDto historyPosteDto) {
        return historyPosteDto.getName() + " [ " + SimpleDateUtils.format(historyPosteDto.getDatePromotion(), SimpleDate.GENERAL_DATE_PATTERN) + " ]";
    }

    protected void initialiseChipCloud() {
        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.none)
                .checkedChipColor(Color.parseColor("#ddaa00"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#efefef"))
                .uncheckedTextColor(Color.parseColor("#666666"))
                .showClose(Color.parseColor("#a6a6a6"), 500)
                .useInsetPadding(true);
        chipCloud = new ChipCloud(getContext(), chipFlexBoxLayout, config);

        chipCloud.setDeleteListener(new ChipDeletedListener() {
            @Override
            public void chipDeleted(int i, String s) {
                historyPosteDtos.remove(i);
            }
        });


    }


    @Click(R.id.rBtnCreateEmployeHomme)
    protected void hommeClicked() {
        isMale = true;
    }

    @Click(R.id.rBtnCreateEmployeFemme)
    protected void femmeClicked() {
        isMale = false;
    }

    @Click(R.id.btnCreaterEmploye)
    protected void onCreateEmployeClicked() {
        validator.validate();
    }

    @Override
    public void handleError(Throwable error) {
        Toast.makeText(pActivity, "Une erreur vient de survenir", Toast.LENGTH_SHORT).show();
        error.printStackTrace();
    }

    @Click(R.id.btnCreateEmployeAddPoste)
    protected void onAddPosteClicked() {

        addPosteDialog.show();
    }


}
