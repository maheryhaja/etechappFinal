package mg.etech.mobile.etechapp.presentation.fragments.employe.list.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeViewHolder;

/**
 * Created by maheryHaja on 9/11/2017.
 */

public class CustomFlexibleAdapter extends FlexibleAdapter<IFlexible> {
    public CustomFlexibleAdapter(@NonNull List<IFlexible> items, @Nullable Object listeners) {
        super(items, listeners);
    }


}
