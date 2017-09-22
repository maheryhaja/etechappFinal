package mg.etech.mobile.etechapp.presentation.fragments.employe.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.activities.employe.detailemploye.DetailEmployeActivity_;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragment;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_list_employe)
public class ListEmployeFragment extends AbstractFragment {

    private List<EmployeDto> employeDtos = new ArrayList<>();

    private List<IFlexible> items = new ArrayList<>();
    private EmployeDto selectedEmployeDto;
    private PoleDto poleDto;
    private int selectedId;

    private FlexibleAdapter.OnItemClickListener onClickListener = new FlexibleAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position) {
            selectedEmployeDto = (((SuperListEmployeItem) items.get(position)).getEmployeDto());
            selectedId = (((SuperListEmployeItem) items.get(position)).getItemId());
            onItemClicked();
            return false;
        }
    };
    private FlexibleAdapter<IFlexible> adapter = new FlexibleAdapter<IFlexible>(items, onClickListener);
    ;


    @ViewById(R.id.RVListEmploye)
    RecyclerView listEmployeView;

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;


    private CentralEmployeSynchroSA centralEmployeSynchroSA;
    private Predicate<SuperListEmployeItem> poleDtoFiltre;

    public ListEmployeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
    }

    @AfterViews
    void initAfterViews() {
        adapter = new FlexibleAdapter<IFlexible>(items, onClickListener);
        listEmployeView.setAdapter(adapter);
        listEmployeView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.mItemClickListener = onClickListener;
        adapter.notifyDataSetChanged();


    }

    @AfterInject
    void initAfterInject() {

    }

    public void initFragment() {

    }


    public List<EmployeDto> getEmployeDtos() {
        return employeDtos;
    }

    public void setEmployeDtos(List<EmployeDto> employeDtos) {
        this.employeDtos = employeDtos;
    }

    private void onItemClicked() {
        Log.d("mahery-haja", "item clicked once for all");

        // go to detail activity
        DetailEmployeActivity_.intent(getContext()).employeId(selectedEmployeDto.getId()).itemId(selectedId).start();
    }

    public void setListInitialEmployeObservable(CentralEmployeSynchroSA centralEmployeSynchroSA, PoleDto poleDtoIn) {
        this.centralEmployeSynchroSA = centralEmployeSynchroSA;
        this.poleDto = poleDtoIn;

        //get actual list
        poleDtoFiltre = new Predicate<SuperListEmployeItem>() {
            @Override
            public boolean test(@NonNull SuperListEmployeItem superListEmployeItem) throws Exception {
                return superListEmployeItem.getEmployeDto().getPole().getId() == poleDto.getId();
            }
        };
        centralEmployeSynchroSA
                .getActualList()
                .filter(poleDtoFiltre)
                .subscribe(new Consumer<SuperListEmployeItem>() {
                    @Override
                    public void accept(SuperListEmployeItem superListEmployeItem) throws Exception {
                        items.add(superListEmployeItem);
                        adapter.notifyDataSetChanged();
                    }
                });


        //subscribe for add

        centralEmployeSynchroSA
                .onAddObservable()
                .filter(poleDtoFiltre)
                .subscribe(new Consumer<SuperListEmployeItem>() {
                    @Override
                    public void accept(SuperListEmployeItem superListEmployeItem) throws Exception {
                        Log.d("mahery-haja", "created id from List " + superListEmployeItem.getItemId());
                        items.add(superListEmployeItem);
                        adapter.notifyDataSetChanged();
                    }
                });

        //subscribe for update
        centralEmployeSynchroSA
                .onUpdateObservable()
                .filter(poleDtoFiltre)
                .subscribe(new Consumer<SuperListEmployeItem>() {
                    @Override
                    public void accept(SuperListEmployeItem superListEmployeItem) throws Exception {
                        int position = items.indexOf(superListEmployeItem);
                        items.set(position, superListEmployeItem);
                        adapter.notifyDataSetChanged();
                    }
                });

        //subscribe for delete
        centralEmployeSynchroSA
                .onDeleteObservable()
                .filter(poleDtoFiltre)
                .subscribe(new Consumer<SuperListEmployeItem>() {
                    @Override
                    public void accept(SuperListEmployeItem superListEmployeItem) throws Exception {
                        int position = items.indexOf(superListEmployeItem);
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });


    }


}
