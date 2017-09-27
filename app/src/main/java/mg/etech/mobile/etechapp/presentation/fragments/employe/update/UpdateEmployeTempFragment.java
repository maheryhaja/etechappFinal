package mg.etech.mobile.etechapp.presentation.fragments.employe.update;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;

/**
 * Created by maheryHaja on 9/26/2017.
 */
@EFragment(R.layout.fragment_create_employe)
public class UpdateEmployeTempFragment extends UpdateEmployeFragment{

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    protected String operationName;
    protected Long operationId;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
        Log.d("mahery-haja", "operation name: " + operationName);
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    @Override
    protected EmployeDto performOperation(EmployeDto employeDto) {

        employeDto.setId(savedEmployeDto.getId());
        OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<EmployeDto>();
        employeDtoOperationDto.setId(getOperationId());
        employeDtoOperationDto.setOperationName(operationName);
        employeDtoOperationDto.setData(employeDto);

        if(operationName.equals(OperationType.UPDATE))
        employeDtoOperationDto.setTarget(employeSA.findById(savedEmployeDto.getId()));
        employeDtoOperationDto.setClassName(EmployeDto.class.getName());

        //add an update operation
        operationStackSynchroSA.updateOperation(employeDtoOperationDto);
        return employeDto;
    }
}
