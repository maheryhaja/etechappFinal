package mg.etech.mobile.etechapp.presentation.activities.main.adapter;

import android.support.v4.app.Fragment;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public interface MainPagerAdapterBuilder {
    MainPagerAdapter build();

    MainPagerAdapterBuilder addFragment(Fragment fragment);

    MainPagerAdapterBuilder addFragment(Fragment fragment, String title);
}
