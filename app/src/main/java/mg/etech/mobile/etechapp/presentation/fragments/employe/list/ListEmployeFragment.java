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
import mg.etech.mobile.etechapp.presentation.activities.detailemploye.DetailEmployeActivity_;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_list_employe)
public class ListEmployeFragment extends AbstractFragment {

    private List<EmployeDto> employeDtos = new ArrayList<>();

    private List<IFlexible> items = new ArrayList<>();
    private EmployeDto selectedEmployeDto;

    private FlexibleAdapter.OnItemClickListener onClickListener = new FlexibleAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position) {
            selectedEmployeDto = employeDtos.get(position);
            onItemClicked();
            return false;
        }
    };


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
        FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items, onClickListener);
        listEmployeView.setAdapter(adapter);
        listEmployeView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.mItemClickListener = onClickListener;
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

    private void onItemClicked() {
        Log.d("mahery-haja", "item clicked once for all");

        // go to detail activity
        DetailEmployeActivity_.intent(getContext()).employeId(selectedEmployeDto.getId()).start();

    }




}
