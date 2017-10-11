package mg.etech.mobile.etechapp.presentation.activities.main.customtab;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.reactivex.functions.Consumer;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeFragment;

/**
 * Created by mahery.haja on 11/10/2017.
 */
@EViewGroup(R.layout.viewgroup_custom_tab)
public class CustomTabView extends LinearLayout {

    private PoleDto poleDto;
    private ListEmployeFragment listEmployeFragment;


    @ViewById(R.id.txtCustomTabTitle)
    protected TextView txtTitle;


    @ViewById(R.id.txtCustomTabCount)
    protected TextView txtCount;

    @ViewById(R.id.layoutCustomTabSearch)
    protected LinearLayout layoutSearch;

    public CustomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs, PoleDto poleDto) {
        super(context, attrs);
        this.poleDto = poleDto;
    }

    public CustomTabView(Context context, @Nullable AttributeSet attrs, PoleDto poleDto, @Nullable ListEmployeFragment listEmployeFragment) {
        super(context, attrs);
        this.poleDto = poleDto;

        if (listEmployeFragment != null) {
            this.listEmployeFragment = listEmployeFragment;

            listEmployeFragment
                    .getItemCountObservable()
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            if (txtCount != null) {
                                txtCount.setText("" + integer);
                                txtCount.setTextColor(getResources().getColor(integer > 0 ? R.color.green : R.color.darkGray));
                            }
                        }
                    });

        }
    }

    @AfterViews
    void initAfterViews() {
        txtTitle.setText(poleDto.getIdServer());

    }

    public void afficherRecherche(boolean afficher) {
        layoutSearch.setVisibility(afficher ? VISIBLE : GONE);
    }
}
