package mg.etech.mobile.etechapp.presentation.fragments.employe.update;

import android.net.Uri;

import org.androidannotations.annotations.EFragment;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.create.CreateEmployeFragment;

/**
 * Created by mahery.haja on 26/09/2017.
 */

@EFragment(R.layout.fragment_create_employe)
public class UpdateEmployeFragment extends CreateEmployeFragment {

    protected EmployeDto savedEmployeDto;

    public void initEmployeDto(EmployeDto emp) {
        //set all field in the fragment
        this.btnCreateEmploye.setText(getResources().getText(R.string.btn_text_update_employe));

        savedEmployeDto = emp;
        this.edtNom.setText(emp.getLastName());
        this.edtPrenom.setText(emp.getFirstName());
        this.edtAllias.setText(emp.getAlias());
        this.edtBirthDate.setDate(emp.getBirthDate());
        this.edtHiringDate.setDate(emp.getHiringDate());
        this.edtMail.setText(emp.getMail());
        this.edtMatricule.setText(emp.getMatricule() + "");
        this.spinnerPoleDto.setSelection((int) (emp.getPole().getId() - 1));

        if (emp.getEncodedPhoto() != null) {
            base64PhotoPicker.setPhotowithUri(Uri.parse(emp.getEncodedPhoto()));
        } else if (emp.getPhoto() != null) {
            base64PhotoPicker.setPhotoWithUrl(emp.getPhoto());
        }

        for (HistoryPosteDto historyPosteDto : emp.getPostes()) {
            chipCloud.addChip(chipFromHistoryDto(historyPosteDto));
            historyPosteDtos.add(historyPosteDto);
        }

        if (!emp.isMale()) {
            rGroupMale.check(R.id.rBtnCreateEmployeFemme);
        }

    }


    @Override
    protected EmployeDto performOperation(EmployeDto employeDto) {
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
