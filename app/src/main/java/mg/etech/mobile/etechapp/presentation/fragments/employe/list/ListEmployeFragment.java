package mg.etech.mobile.etechapp.presentation.fragments.employe.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_list_employe)
public class ListEmployeFragment extends Fragment {

    private List<EmployeDto> employeDtos = new ArrayList<>();

    private List<IFlexible> items = new ArrayList<>();


    @ViewById(R.id.RVListEmploye)
    RecyclerView listEmployeView;


    public ListEmployeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        Log.d("mahery-haja", "on create called, hihihi " + items.size());
    }

    @AfterViews
    void initAfterViews() {
        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items);
        listEmployeView.setAdapter(adapter);
        listEmployeView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    public void initFragment() {


    }

    public void populateAdapter() {
        Log.d("mahery-haja", "dtos size " + employeDtos.size());
        for (EmployeDto employeDto : employeDtos) {
            Log.d("mahery-haja", "populate");
            items.add(new ListEmployeItem(employeDto));
        }
    }

    public List<EmployeDto> getEmployeDtos() {
        return employeDtos;
    }

    public void setEmployeDtos(List<EmployeDto> employeDtos) {
        this.employeDtos = employeDtos;
        populateAdapter();
    }


}
