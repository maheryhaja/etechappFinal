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

import mg.etech.mobile.etechapp.donnee.dto.PosteDto;

/**
 * Created by maheryHaja on 9/18/2017.
 */

public class PosteSpinnerAdapter extends ArrayAdapter<String>{

    private Context context;
    private List<PosteDto> posteDtos;


    public PosteSpinnerAdapter(@NonNull Context ctx, @LayoutRes int resource) {
        super(ctx, resource);
        this.context = ctx;
    }

    public PosteSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, List<PosteDto> posteDtos) {
        super(context, resource);
        this.posteDtos = posteDtos;
    }

    @Override
    public int getCount() {
        return posteDtos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = new TextView(getContext());
        textView.setText(posteDtos.get(position).getName());
        return textView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return posteDtos.get(position).getName();
    }
}
