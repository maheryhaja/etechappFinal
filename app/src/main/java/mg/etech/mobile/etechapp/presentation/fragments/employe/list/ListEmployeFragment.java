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
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.presentation.activities.employe.detailemploye.DetailEmployeActivity_;
import mg.etech.mobile.etechapp.presentation.fragments.AbstractFragment;
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
    private FlexibleAdapter<IFlexible> adapter;

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

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;


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
        DetailEmployeActivity_.intent(getContext()).employeId(selectedEmployeDto.getId()).start();

    }

    public void setListInitialEmployeObservable(Observable<EmployeDto> listEmployeObservable, Observable<OperationDto<EmployeDto>> operationDtoObservable, PoleDto poleDtoIn) {
        this.poleDto = poleDtoIn;
        listEmployeObservable
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<EmployeDto>() {
                    @Override
                    public boolean test(EmployeDto employeDto) {
                        return employeDto.getPole().getId() == poleDto.getId();
                    }
                })
                .subscribe(new Consumer<EmployeDto>() {
                    @Override
                    public void accept(EmployeDto employeDto) throws Exception {
//                        Log.d("mahery-haja", "Filter "+poleDto.getName()+" :"+employeDto.getLastName()+" "+employeDto.getLastName());
                        items.add(new ListEmployeItem(employeDto));
                        employeDtos.add(employeDto);
                    }
                });

        subscribeForEmployeOperation(operationDtoObservable);


    }


    private void subscribeForEmployeOperation(Observable<OperationDto<EmployeDto>> observable) {


        observable
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<OperationDto<EmployeDto>>() {
                    @Override
                    public boolean test(OperationDto<EmployeDto> employeDtoOperationDto) {
                        return employeDtoOperationDto.getData().getPole().getId() == poleDto.getId();
                    }
                })
                .subscribe(new Consumer<OperationDto<EmployeDto>>() {
                    @Override
                    public void accept(OperationDto<EmployeDto> employeDtoOperationDto) throws Exception {


                        items.add(new ListEmployeItemTemp(employeDtoOperationDto.getData()));
                        employeDtos.add(employeDtoOperationDto.getData());
                    }
                });
    }


}
