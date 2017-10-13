package mg.etech.mobile.etechapp.presentation.activities.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.animation.SimpleReboundAnimator;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.activities.employe.createEmloye.CreateEmployeActivity_;
import mg.etech.mobile.etechapp.presentation.activities.login.LoginActivity_;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapter;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapterBuilder;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapterBuilderImpl;
import mg.etech.mobile.etechapp.presentation.activities.main.customtab.CustomTabProvider;
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
@OptionsMenu(R.menu.menu_filter)
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

    @ViewById(R.id.btnSynchroLauncher)
    SynchroLauncher btnSynchroLauncher;

    private SearchView searchView;

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

    @OptionsMenuItem(R.id.action_logout)
    void logoutClicked(MenuItem logoutItem) {

        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                preferenceSA.setUserConnected(false);
                LoginActivity_.intent(MainActivity.this).start();
                finish();
                return true;
            }
        });

    }

    private PublishSubject<String> searchSubject = PublishSubject.create();
    private PublishSubject<Boolean> isSearchingSubject = PublishSubject.create();


    @AfterViews
    void initAfterViews() {
        initViewPager();
        showAddButton();
        pullSynchroSA.launch();

        Consumer<Boolean> synchroRunningConsummer = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnSynchroLauncher.toggle(aBoolean);
            }
        };

        btnSynchroLauncher
                .getFront()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pullSynchroSA.launch();
                        commandInvoker.launch();
                    }
                });

        Observable
                .combineLatest(
                        pullSynchroSA.getRunningObservable(),
                        commandInvoker.getRunningObservable(),
                        new BiFunction<Boolean, Boolean, Boolean>() {
                            @Override
                            public Boolean apply(@NonNull Boolean pullBoolean, @NonNull Boolean invokerBoolean) throws Exception {

                                Log.d("mahery-haja", "emitting pull:" + pullBoolean + " invoker:" + invokerBoolean + " miodine:" + (pullBoolean || invokerBoolean));

                                return pullBoolean || invokerBoolean;

                            }
                        }
                )
                .subscribe(synchroRunningConsummer);

//
//        pullSynchroSA
//                .getRunningObservable()
//                .subscribe(synchroRunningConsummer);
//
//        commandInvoker
//                .getRunningObservable()
//                .subscribe(synchroRunningConsummer);
    }


    @OptionsMenuItem(R.id.action_search)
    void searchInjected(MenuItem searchItem) {
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchSubject.onNext(s);
                isSearchingSubject.onNext(s.equals("") || s == null ? false : true);
                return true;
            }
        });

        searchView
                .setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        isSearchingSubject.onNext(false);
                        searchSubject.onNext("");
                        return true;
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
            fragment.setQueryListener(searchSubject);
            fragment.initFragment();
            mainPagerAdapterBuilder = mainPagerAdapterBuilder.addFragment(fragment, poleDto.getIdServer());
        }

        MainPagerAdapter mainPagerAdapter = mainPagerAdapterBuilder.build();

        final CustomTabProvider customTabProvider = new CustomTabProvider(poleDtoList);

        viewPager.setAdapter(mainPagerAdapter);
        viewPagerTab.setCustomTabView(customTabProvider);

        isSearchingSubject
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        customTabProvider.afficherSearch(aBoolean);
                    }
                });


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


}
