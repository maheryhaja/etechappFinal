package mg.etech.mobile.etechapp.presentation.fragments.employe.detail;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.animation.SimpleReboundAnimator;
import mg.etech.mobile.etechapp.commun.constante.SimpleDate;
import mg.etech.mobile.etechapp.commun.utils.date.SimpleDateUtils;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.presentation.customviews.PicassoImageView;
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

    @ViewById(R.id.pImageViewDetailPhoto)
    PicassoImageView pPhotoImageView;

    @ViewById(R.id.txtPosteActuel)
    TextView txtPosteActuel;

    private EmployeDto employeDto;
    private boolean isBack = false;

    public DetailEmployeFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void initAfterViews() {

        //initialize flipview
        //retrieveEmploye();

        easyFlipView.setVisibility(View.VISIBLE);
        easyFlipView.setFlipOnTouch(false);
        retrieveEmployeFromItemId();
        actualiserAffichage();


    }

    private void initializePosteHistoryDetail() {
        List<IFlexible> items = new ArrayList<>();

        List<HistoryPosteDto> historyPosteDtos = employeDto.getPostes();

        if (historyPosteDtos != null) {

            // trier par date
            Collections.sort(historyPosteDtos, new Comparator<HistoryPosteDto>() {
                @Override
                public int compare(HistoryPosteDto lhs, HistoryPosteDto rhs) {
                    long differenceTime = lhs.getDatePromotion().getTime() - rhs.getDatePromotion().getTime();
                    return differenceTime < 0 ? -1 : (differenceTime == 0) ? 0 : 1;
                }
            });

            for (int i = 0; i < historyPosteDtos.size(); i++) {
                HistoryPosteDto historyPosteDto = historyPosteDtos.get(i);
                historyPosteDto.setId((long) (i + 1));
                items.add(new PosteHistoryFlexibleItem(historyPosteDto));
            }


        }

        if (historyPosteDtos.size() == 0) {
            txtHistoryNoPoste.setVisibility(View.VISIBLE);
        } else {
            definirPosteActuel(historyPosteDtos.get(historyPosteDtos.size() - 1).getName());
        }

        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void definirPosteActuel(String poste) {
        txtPosteActuel.setText(poste);

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

        //set Mai0l
        txtMail.setText(getFormatedMail(employeDto.getMail()));

        // set Age
        txtAge.setText(getFormatedAge(employeDto.getBirthDate()));

        initializePosteHistoryDetail();

//        if (employeDto.getPhoto() != null)
//        setImage(employeDto.getPhoto());

        setPhotoIsMale(employeDto.isMale());

        setPicassoImage(employeDto);
    }


    public void setPicassoImage(EmployeDto employeDto) {
        if (employeDto.getEncodedPhoto() != null) {
            pPhotoImageView.setPhotoWithUri(Uri.parse(employeDto.getEncodedPhoto()));
        } else if (employeDto.getPhoto() != null) {
            pPhotoImageView.setPhotoWithUrl(employeDto.getPhoto());
        } else {
            pPhotoImageView.setFrontImage(employeDto.isMale() ? R.drawable.ic_default_men_150 : R.drawable.ic_default_women_150);
        }
        new SimpleReboundAnimator(pPhotoImageView).bounce();
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



    private void retrieveEmployeFromItemId() {
        employeDto = centralEmployeSynchroSA
                .findByitemId(itemId);

    }

    @Click(R.id.btnDetailEmployeFlipToHistorique)
    void onShowHistoryClicked() {
        easyFlipView.flipTheView();
        isBack = true;
    }

    public boolean processBack() {
        if (isBack) {
            easyFlipView.flipTheView();
            isBack = false;
            return false;
        }
        return true;
    }

}
