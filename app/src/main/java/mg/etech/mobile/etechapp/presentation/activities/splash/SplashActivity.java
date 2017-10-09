package mg.etech.mobile.etechapp.presentation.activities.splash;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.activities.login.LoginActivity_;
import mg.etech.mobile.etechapp.presentation.activities.main.MainActivity_;
import mg.etech.mobile.etechapp.service.applicatif.PreferenceSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.operation.OperationSA;
import mg.etech.mobile.etechapp.service.applicatif.operation.OperationSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.preferences.PreferenceSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.back.BackSynchronizerSAImpl;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    public static final int TOTAL_DURATION = 3500;
    public static final int NUMBER_OF_TICK = 10;
    @Bean(PreferenceSAImpl.class)
    PreferenceSA preferenceSA;

    @ViewById(R.id.circularFillableSplash)
    CircularFillableLoaders fillableLoader;
    private Observable<Integer> fiveSecondsObservable;

    @Bean(BackSynchronizerSAImpl.class)
    BackSynchronizerSA backSynchronizerSA;

    @Bean(OperationSAImpl.class)
    OperationSA operationSA;

    @AfterViews
    public void initAfterViews() {
        initializeIntervalObservable();



//        backSynchronize();


        fiveSecondsObservable
                    .subscribe(new Consumer<Integer>() {
                                   @Override
                                   public void accept(Integer integer) throws Exception {
                                       fillableLoader.setProgress(integer);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                }
                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    if (preferenceSA.isUserConnected()) {
                                        goToMainActivity();
//                                        TestAnimationActivity_.intent(SplashActivity.this).start();
                                    } else {
                                        goToLoginActivity();
                                    }
                                }
                            }
                    );
        }


    private void backSynchronize() {
        Observable
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        backSynchronizerSA.synch();

                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                               @Override
                               public void accept(Boolean aBoolean) throws Exception {
                                   Log.d("mahery-haja", "synchronisation success");
                                   goToMainActivity();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("mahery-haja", "synchronisation error");
                                throwable.printStackTrace();
                            }
                        }
                );
    }


    private void initializeIntervalObservable() {
        fiveSecondsObservable = Observable
                .interval(TOTAL_DURATION / NUMBER_OF_TICK, TimeUnit.MILLISECONDS)
                .scan(new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong, @NonNull Long aLong2) throws Exception {
                        return aLong2 + 1;
                    }
                })
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(@NonNull Long aLong) throws Exception {
                        return aLong >= NUMBER_OF_TICK;
                    }
                })
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return aLong.intValue() * 100 / NUMBER_OF_TICK;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    // go to Login Activity
    private void goToLoginActivity() {
        LoginActivity_.intent(this).start();
        finish();
    }


    //vers Main Activity
    private void goToMainActivity() {
        MainActivity_.intent(this).start();
        finish();
    }

}
