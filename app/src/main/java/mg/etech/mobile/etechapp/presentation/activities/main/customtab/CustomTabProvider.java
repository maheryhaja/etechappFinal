package mg.etech.mobile.etechapp.presentation.activities.main.customtab;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.activities.main.adapter.MainPagerAdapter;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeFragment;

/**
 * Created by mahery.haja on 11/10/2017.
 */

public class CustomTabProvider implements SmartTabLayout.TabProvider {

    private List<PoleDto> poleDtoList = new ArrayList<>();
    private List<CustomTabView> customTabViews = new ArrayList<>();

    public CustomTabProvider(List<PoleDto> poleDtoList) {
        this.poleDtoList = poleDtoList;
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {

        ListEmployeFragment listEmployeFragment = null;
        if (adapter instanceof MainPagerAdapter) {
            MainPagerAdapter mainPagerAdapter = ((MainPagerAdapter) adapter);

            Fragment fragment = mainPagerAdapter.getItem(position);

            if (fragment instanceof ListEmployeFragment) {
                listEmployeFragment = ((ListEmployeFragment) fragment);
            }
        }


        TextView textView = new TextView(container.getContext());

        CustomTabView customTabView = CustomTabView_.build(container.getContext(), null, poleDtoList.get(position), listEmployeFragment);
        customTabViews.add(customTabView);
        return customTabView;
    }

    public void afficherSearch(boolean afficher) {
        for (CustomTabView customTabView : customTabViews) {
            customTabView.afficherRecherche(afficher);
        }
    }
}
