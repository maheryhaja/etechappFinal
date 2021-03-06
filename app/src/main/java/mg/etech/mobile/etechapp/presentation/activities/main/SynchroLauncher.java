package mg.etech.mobile.etechapp.presentation.activities.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.config.ConfigUrl;

/**
 * Created by mahery.haja on 11/10/2017.
 */
@EViewGroup(R.layout.viewgroup_synchro_launcher)
public class SynchroLauncher extends LinearLayout {

    @ViewById(R.id.flipViewSynchroLauncher)
    EasyFlipView flipView;

    @ViewById(R.id.imageViewLaunchSynchro)
    ImageView launchSynchro;

    @ViewById(R.id.imageViewIsSynchroing)
    ImageView isSynchroing;

    protected Stack<Boolean> runningProcess = new Stack<>();
    protected boolean isFront = true;

    public SynchroLauncher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @AfterViews
    void initAfterViews() {
        rotateBack();
        flipView.setFlipOnTouch(false);

        checkConnection();


    }

    private void checkConnection() {
        ReactiveNetwork.observeInternetConnectivity(new SocketInternetObservingStrategy(), ConfigUrl.BASE_URL)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        launchSynchro.setImageDrawable(getResources().getDrawable(aBoolean ? R.drawable.ic_launch_synchro : R.drawable.ic_no_internet));
                    }
                })
        ;
    }

    public void rotateBack() {
        Animation rotationAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotationAnimation.setFillAfter(true);
        isSynchroing.startAnimation(rotationAnimation);
    }

    public void requestProcess() {
        if (runningProcess.empty()) {
            showBack();
        }
        runningProcess.add(true);


    }

    public void requestStop() {
        if (!runningProcess.empty()) {
            runningProcess.pop();

            if (runningProcess.empty()) {
                showFront();
            }
        } else {
            showFront();
        }
    }

    public void showBack() {
        Log.d("mahery-haja", "showing back " + flipView.isFrontSide());
        if (isFront && flipView.isFrontSide()) {
            flipView.flipTheView(true);
            Log.d("mahery-haja", " showing and flipping back");
        }
        isFront = false;
    }

    public void showFront() {
        Log.d("mahery-haja", "showing front" + flipView.isBackSide());
        if (!isFront && flipView.isBackSide()) {
            flipView.flipTheView(true);
            Log.d("mahery-haja", " showing and flipping front " + flipView.isFrontSide());
            if (!flipView.isFrontSide()) {
                // flip echoue retry after 200 ms
                isFront = false;
                Observable
                        .timer(200, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                showFront();
                            }
                        });
            } else
                isFront = true;
        } else

        isFront = true;
    }

    public void toggle(boolean status) {
        if (status) {
            showBack();
        } else {
            showFront();
        }
    }

    public View getFront() {
        return launchSynchro;
    }


}
