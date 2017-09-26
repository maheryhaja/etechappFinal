package mg.etech.mobile.etechapp.presentation.fragments.employe.update;

import org.androidannotations.annotations.EFragment;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;
import mg.etech.mobile.etechapp.service.applicatif.operation.commands.CreateEmployeCommand;

/**
 * Created by mahery.haja on 26/09/2017.
 */

@EFragment(R.layout.fragment_create_employe)
public class UpdateEmployeFragment extends CreateEmployeFragment {

    protected EmployeDto savedEmployeDto;

    public void initEmployeDto(EmployeDto emp) {
        //set all field in the fragment
        savedEmployeDto = emp;
        this.edtNom.setText(emp.getLastName());
        this.edtPrenom.setText(emp.getFirstName());
        this.edtAllias.setText(emp.getAlias());
        this.edtBirthDate.setDate(emp.getHiringDate());
        this.edtHiringDate.setDate(emp.getHiringDate());
        this.edtMail.setText(emp.getMail());
        this.edtMatricule.setText(emp.getMatricule() + "");
        this.spinnerPoleDto.setSelection((int) (emp.getPole().getId() - 1));


        for (HistoryPosteDto historyPosteDto : emp.getPostes()) {
            chipCloud.addChip(chipFromHistoryDto(historyPosteDto));
        }

    }


    @Override
    protected EmployeDto performOperation(EmployeDto employeDto) {
        CreateEmployeCommand command;
        employeDto.setId(savedEmployeDto.getId());
        OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<EmployeDto>();
        employeDtoOperationDto.setOperationName(OperationType.UPDATE);
        employeDtoOperationDto.setData(employeDto);
        employeDtoOperationDto.setTarget(savedEmployeDto);
        employeDtoOperationDto.setClassName(EmployeDto.class.getName());
        operationStackSynchroSA.addOperation(employeDtoOperationDto);

        return employeDto;
    }

    @Override
    protected void getEmployeDtofromForm() {
        super.getEmployeDtofromForm();
    }
}
