package mg.etech.mobile.etechapp.presentation.fragments.employe.detail;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
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
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSAImpl;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_detail_employe)
public class DetailEmployeFragment extends Fragment {

    @FragmentArg("emloyeId")
    Long employeId;

    @FragmentArg("itemId")
    int itemId;

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

    @Bean(CentralEmployeSynchroSAImpl.class)
    CentralEmployeSynchroSA centralEmployeSynchroSA;

    @ViewById(R.id.flipViewDetailEmploye)
    EasyFlipView easyFlipView;


    @ViewById(R.id.cardDetailBack)
    CardView backCardView;

    @ViewById(R.id.recyclerView_detailHistoryPoste)
    RecyclerView recyclerView;

    @ViewById(R.id.txtHistoryNoPoste)
    TextView txtHistoryNoPoste;

    private EmployeDto employeDto;

    public DetailEmployeFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void initAfterViews() {

        //initialize flipview
        //retrieveEmploye();

        easyFlipView.setVisibility(View.VISIBLE);
        retrieveEmployeFromItemId();
        actualiserAffichage();


    }

    private void initializePosteHistoryDetail() {
        List<IFlexible> items = new ArrayList<>();

        List<HistoryPosteDto> historyPosteDtos = employeDto.getPostes();

        if (historyPosteDtos != null) {
            for (HistoryPosteDto historyPosteDto : employeDto.getPostes()) {
                Log.d("mahery-haja", "initialisation poste " + historyPosteDto.getName());
                items.add(new PosteHistoryFlexibleItem(historyPosteDto));
            }
        }

        if (historyPosteDtos.size() == 0) {
            txtHistoryNoPoste.setVisibility(View.VISIBLE);
        }


        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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

        initializePosteHistoryDetail();

        setImage(employeDto.getPhoto());
    }

    private void setImage(String imageURL) {

        if (imageURL != null && !imageURL.equals("") && !imageURL.isEmpty()) {

            if (itemId > 0) {

                Picasso
                        .with(getContext())
                        .load(ConfigUrl.BASE_URL + imageURL)
                        .into(photoImageView);
            } else {

                // case from temp
                byte[] decodeString = Base64.decode(imageURL, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                if (bitmap != null) {
                    this.photoImageView.setImageBitmap(bitmap);
                } else {
                    Log.d("mahery-haja", "erreur de transformation bitmap");
                }
            }
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

    private void retrieveEmployeFromItemId() {
        employeDto = centralEmployeSynchroSA
                .findByitemId(itemId);

    }

    @Click(R.id.btnDetailEmployeFlipToHistorique)
    void onShowHistoryClicked() {
        easyFlipView.flipTheView();

    }

}
