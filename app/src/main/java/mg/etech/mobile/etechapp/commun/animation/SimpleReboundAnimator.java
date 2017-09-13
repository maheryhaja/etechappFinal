package mg.etech.mobile.etechapp.commun.animation;

import android.util.Log;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by mahery.haja on 13/09/2017.
 */

public class SimpleReboundAnimator {

    private View view;

    public SimpleReboundAnimator(View view) {
        this.view = view;
    }

    public void bounce() {
        SpringSystem springSystem = SpringSystem.create();

        Spring spring = springSystem.createSpring();
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float value = (float) spring.getCurrentValue();

                Log.d("mahery-haja", "Actual value:" + value);

                float scale = 1f - (value * 0.5f);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });

        spring.setEndValue(1);


    }

}
