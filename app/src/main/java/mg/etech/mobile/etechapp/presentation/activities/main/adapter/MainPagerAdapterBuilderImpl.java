package mg.etech.mobile.etechapp.presentation.activities.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahery.haja on 08/09/2017.
 */
public class MainPagerAdapterBuilderImpl implements MainPagerAdapterBuilder {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    public static final String DEFAULT_FRAGMENT_TITLE = "fragment ";
    private FragmentManager fragmentManager;

    public MainPagerAdapterBuilderImpl() {

    }

    public MainPagerAdapterBuilder withFragmentManager(FragmentManager fm) {
        this.fragmentManager = fm;
        return this;
    }


    @Override
    public MainPagerAdapter build() {
        return new MainPagerAdapter(fragmentManager, fragments, titles);
    }

    @Override
    public MainPagerAdapterBuilder addFragment(Fragment fragment) {
        this.addFragment(fragment, DEFAULT_FRAGMENT_TITLE);
        return this;
    }

    @Override
    public MainPagerAdapterBuilder addFragment(Fragment fragment, String titre) {
        fragments.add(fragment);
        titles.add(titre);
        return this;
    }
}
