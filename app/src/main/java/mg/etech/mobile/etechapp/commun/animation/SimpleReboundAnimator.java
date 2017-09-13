package mg.etech.mobile.etechapp.commun.animation;

import android.util.Log;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

/**
 * Created by mahery.haja on 13/09/2017.
 */

public class SimpleReboundAnimator {

    private View view;
    private Spring spring;

    public SimpleReboundAnimator(View view) {
        this.view = view;
    }

    public void bounce() {
        SpringSystem springSystem = SpringSystem.create();

        spring = springSystem.createSpring();
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float value = (float) spring.getCurrentValue();

                if(value > 0.4f)
                    view.setVisibility(View.VISIBLE);

                float scale = value;
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });

        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(55, 2));
        spring.setEndValue(1);


    }


}
