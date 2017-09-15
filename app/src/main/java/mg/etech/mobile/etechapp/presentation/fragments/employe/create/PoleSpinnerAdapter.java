package mg.etech.mobile.etechapp.presentation.fragments.employe.create;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;

/**
 * Created by mahery.haja on 14/09/2017.
 */

public class PoleSpinnerAdapter extends ArrayAdapter<String> {

    private List<PoleDto> poleDtos;
    private Context context;

    public List<PoleDto> getPoleDtos() {
        return poleDtos;
    }

    public void setPoleDtos(List<PoleDto> poleDtos) {
        this.poleDtos = poleDtos;
    }

    public PoleSpinnerAdapter(@NonNull Context ctx, @LayoutRes int resource) {
        super(ctx, resource);
        context = ctx;
    }

    public PoleSpinnerAdapter(@NonNull Context ctx, @LayoutRes int resource, List<PoleDto> poleDtos) {
        super(ctx, resource);
        this.poleDtos = poleDtos;
        context = ctx;
    }

    @Override
    public int getCount() {
        return poleDtos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(poleDtos.get(position).getName());
        textView.setTextColor(context.getResources().getColor(R.color.white));
        return textView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return poleDtos.get(position).getName();
    }
}
