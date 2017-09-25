package mg.etech.mobile.etechapp.presentation.activities.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.reactivex.Observable;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.animation.SimpleReboundAnimator;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
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
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker.CommandInvoker;
import mg.etech.mobile.etechapp.service.applicatif.synchro.commandInvoker.CommandInvokerImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.pull.PullSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.pull.PullSynchroSAImpl;

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

    @Bean(DataBaseSynchroSAImpl.class)
    DataBaseSynchroSA dataBaseSynchroSA;

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;

    @Bean(CentralEmployeSynchroSAImpl.class)
    CentralEmployeSynchroSA centralEmployeSynchroSA;

    @Bean(CommandInvokerImpl.class)
    CommandInvoker commandInvoker;

    @Bean(PullSynchroSAImpl.class)
    PullSynchroSA pullSynchroSA;

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
            fragment.setListInitialEmployeObservable(centralEmployeSynchroSA, poleDto);
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

    public Observable<EmployeDto> subscribeForEmployeList() {
        return dataBaseSynchroSA
                .getInitialEmployeList();
    }

    @Click(R.id.btnSynchro)
    void onSynchroClicked() {
        //commandInvoker.processStack();
        pullSynchroSA.launch();
    }

}
