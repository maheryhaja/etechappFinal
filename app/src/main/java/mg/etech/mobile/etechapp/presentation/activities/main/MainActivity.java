package mg.etech.mobile.etechapp.presentation.activities.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.clans.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.animation.SimpleReboundAnimator;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye.CreateEmployeActivity_;
import mg.etech.mobile.etechapp.presentation.activities.login.LoginActivity_;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapter;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapterBuilder;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapterBuilderImpl;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeFragment_;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.preferences.PreferenceSA;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    @Bean(PreferenceSAImpl.class)
    PreferenceSA preferenceSA;

    @ViewById(R.id.viewPagerMain)
    ViewPager viewPager;

    @ViewById(R.id.mainViewPagerTab)
    SmartTabLayout viewPagerTab;

    @ViewById(R.id.btnAddEmploye)
    FloatingActionButton addEmployeButton;


    @Bean(PoleSAImpl.class)
    PoleSA poleSA;

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    @Click(R.id.btnLogout)
    void logoutClicked() {
        preferenceSA.setUserConnected(false);
        LoginActivity_.intent(this).start();
        finish();
    }


    @AfterViews
    void initAfterViews() {
        initViewPager();
        showAddButton();
    }

    private void showAddButton() {
        new SimpleReboundAnimator(addEmployeButton).bounce();
    }

    private void initViewPager() {
        MainPagerAdapterBuilder mainPagerAdapterBuilder = new MainPagerAdapterBuilderImpl()
                .withFragmentManager(getSupportFragmentManager());

        List<PoleDto> poleDtoList = poleSA.findAll();


        /***
         * TODO mettre ici le recepteur venant de la base de donnees
         */
        for (PoleDto poleDto : poleDtoList) {
            ListEmployeFragment_ fragment = new ListEmployeFragment_();
            fragment.setEmployeDtos(employeSA.findByPole(poleDto));
            Log.d("mahery-haja", "another " + fragment.getEmployeDtos().size());
            fragment.initFragment();
            mainPagerAdapterBuilder = mainPagerAdapterBuilder.addFragment(fragment, poleDto.getIdServer());
        }

        MainPagerAdapter mainPagerAdapter = mainPagerAdapterBuilder.build();

        viewPager.setAdapter(mainPagerAdapter);
        viewPagerTab.setViewPager(viewPager);

    }

    @Click(R.id.btnAddEmploye)
    void onAddButtonClicked() {
        CreateEmployeActivity_.intent(this).start();
    }

}
