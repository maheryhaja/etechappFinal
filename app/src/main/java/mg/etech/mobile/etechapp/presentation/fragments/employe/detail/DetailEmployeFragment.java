package mg.etech.mobile.etechapp.presentation.fragments.employe.detail;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
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
        txtNomEtPrenom.setText(employeDto.getFirstName() + " " + employeDto.getLastName());
        txtMatricule.setText(employeDto.getMatricule() + "");
        txtAlias.setText(employeDto.getAlias());
        txtBirthDate.setText(employeDto.getBirthDate().toString());
        txtHiringDate.setText(employeDto.getHiringDate().toString());
        txtMail.setText(employeDto.getMail());
        // ajouter age
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
