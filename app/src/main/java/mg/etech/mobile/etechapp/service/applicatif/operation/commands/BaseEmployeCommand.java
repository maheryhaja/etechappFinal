package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;

import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactoryImpl_;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl_;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl_;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class BaseEmployeCommand {
    protected EmployeSA employeSA;
    protected EmployeBdl employeBdl;
    protected EmployeWsDtoFromDtoFactory employeWsDtoFromDtoFactory;
    protected Context context;
    protected OperationDto<EmployeDto> employeDtoOperationDto;

    public BaseEmployeCommand(Context context) {
        this.context = context;
        employeBdl = EmployeBdlImpl_.getInstance_(context);
        employeWsDtoFromDtoFactory = EmployeWsDtoFromDtoFactoryImpl_.getInstance_(context);
        employeSA = EmployeSAImpl_.getInstance_(context);
    }

    public void setOperation(OperationDto<EmployeDto> employeDtoOperationDto) {
        this.employeDtoOperationDto = employeDtoOperationDto;
    }
}
