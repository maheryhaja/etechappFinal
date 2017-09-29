package mg.etech.mobile.etechapp.presentation.fragments.employe.list.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;

/**
 * Created by maheryHaja on 9/11/2017.
 */

public class CustomFlexibleAdapter<T extends IFlexible> extends FlexibleAdapter<T> {


    public CustomFlexibleAdapter(@NonNull List<T> items) {
        super(items);
    }

    public CustomFlexibleAdapter(@NonNull List<T> items, @Nullable Object listeners) {
        super(items, listeners);
        mUpdateListener = new OnUpdateListener() {
            @Override
            public void onUpdateEmptyView(int size) {

            }
        };
    }


    @Override
    protected boolean filterObject(T item, String constraint) {
        boolean isOk = (item instanceof SuperListEmployeItem);

        if (isOk) {
            SuperListEmployeItem superListEmployeItem = (SuperListEmployeItem) item;
            EmployeDto employeDto = superListEmployeItem.getEmployeDto();

            Pattern pattern = Pattern.compile(".*" + constraint + ".*", Pattern.CASE_INSENSITIVE);
            String input = employeDto.getFirstName() + " " + employeDto.getLastName() + " " + employeDto.getAlias();


            Matcher matcher = pattern.matcher(input);
            isOk = matcher.matches();
            Log.d("mahery-haja", "comare: " + constraint + " " + input + " " + isOk);
        }
        Log.d("mahery-haja", "call filter " + isOk);
        return isOk;


    }




}
