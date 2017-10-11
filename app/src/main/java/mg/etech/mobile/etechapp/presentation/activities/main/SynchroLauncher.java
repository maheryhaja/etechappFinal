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

import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Stack;

import mg.etech.mobile.etechapp.R;

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
        Log.d("mahery-haja", "showing back");
        if (isFront) {
            flipView.flipTheView();
        }
        isFront = false;
    }

    public void showFront() {
        Log.d("mahery-haja", "showing front");
        if (!isFront) {
            flipView.flipTheView();
        }
        isFront = true;
    }

    public void toggle(boolean status) {
        if (status) {
            requestProcess();
        } else {
            requestStop();
        }
    }

    public View getFront() {
        return launchSynchro;
    }


}
