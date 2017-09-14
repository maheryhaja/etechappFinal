package mg.etech.mobile.etechapp.presentation.fragments.employe.detail;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.animation.SimpleReboundAnimator;
import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import mg.etech.mobile.etechapp.commun.constante.SimpleDate;
import mg.etech.mobile.etechapp.commun.utils.date.SimpleDateUtils;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_detail_employe)
public class DetailEmployeFragment extends Fragment {

    @FragmentArg("emloyeId")
    Long employeId;

    @ViewById(R.id.txtDetailAge)
    TextView txtAge;

    @ViewById(R.id.txtDetailNom)
    TextView txtNomEtPrenom;

    @ViewById(R.id.txtDetailBirthDate)
    TextView txtBirthDate;

    @ViewById(R.id.txtDetailHiringDate)
    TextView txtHiringDate;

    @ViewById(R.id.txtDetailMail)
    TextView txtMail;

    @ViewById(R.id.txtDetailAlias)
    TextView txtAlias;

    @ViewById(R.id.txtDetailMatricule)
    TextView txtMatricule;

    @ViewById(R.id.imageView_detailPhoto)
    ImageView photoImageView;

    @ViewById(R.id.imageView_detailIsMale)
    ImageView isMaleImageView;

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;


    private EmployeDto employeDto;

    public DetailEmployeFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void initAfterViews() {
        retrieveEmploye();
    }

    private void actualiserAffichage() {
        //set nom + prenom
        txtNomEtPrenom.setText(employeDto.getFirstName() + " " + employeDto.getLastName());
        //set matricule
        txtMatricule.setText(getFormatedMatricule(employeDto.getMatricule()));

        //set Alias
        txtAlias.setText(getFormatedAllias(employeDto.getAlias()));

        //set Birth Date
        txtBirthDate.setText(getFormatedBirthDate(employeDto.getBirthDate(), employeDto.isMale()));
        txtHiringDate.setText(getFormatedHiringDate(employeDto.getHiringDate(), employeDto.isMale()));

        //set Mail
        txtMail.setText(getFormatedMail(employeDto.getMail()));

        // set Age
        txtAge.setText(getFormatedAge(employeDto.getBirthDate()));

        setImage(employeDto.getPhoto());
    }

    private void setImage(String imageURL) {

        if (imageURL != null && !imageURL.equals("") && !imageURL.isEmpty()) {
            Picasso
                    .with(getContext())
                    .load(ConfigUrl.BASE_URL + imageURL)
                    .into(photoImageView);
        }

        new SimpleReboundAnimator(photoImageView).bounce();


    }

    private void setPhotoIsMale(boolean isMale) {
        if (!isMale) {
            isMaleImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_femme));
        }
    }

    private String getFormatedAge(Date birthDate) {
        int age = SimpleDateUtils.getAge(birthDate);
        return getContext().getResources().getString(R.string.detail_age, age);
    }

    private String getFormatedMatricule(Long matricule) {
        return getContext().getResources().getString(R.string.detail_matricule, matricule);
    }

    private String getFormatedDate(Date date) {
        return SimpleDateUtils.format(date, SimpleDate.GENERAL_DATE_PATTERN);
    }

    private String getFormatedMail(String mail) {
        return getContext().getResources().getString(R.string.detail_mail, mail);
    }

    private String getFormatedAllias(String allias) {
        return getContext().getResources().getString(R.string.detail_allias, allias);
    }

    private String getFormatedHiringDate(Date hiringDate, boolean isMale) {
        return getContext().getResources().getString(R.string.detail_embauche, getFormatedDate(hiringDate), isMale ? "" : "e");
    }

    private String getFormatedBirthDate(Date birthDate, boolean isMale) {
        return getContext().getResources().getString(R.string.detail_birth_date, getFormatedDate(birthDate), isMale ? "" : "e");
    }


    private void retrieveEmploye() {
        Observable
                .fromCallable(new Callable<EmployeDto>() {
                    @Override
                    public EmployeDto call() throws Exception {
                        return employeSA.findById(employeId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeDto>() {
                               @Override
                               public void accept(EmployeDto emp) throws Exception {
                                   employeDto = emp;

                                   Log.d("mahery-haja", "emloye found");

                                   actualiserAffichage();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("mahery-haja", "base de donnees inaccessible pour " + employeId);

                            }
                        }
                );
    }

}
