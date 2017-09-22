package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactoryImpl_;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl_;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl_;

/**
 * Created by mahery.haja on 20/09/2017.
 */
public class CreateEmployeCommand implements OperationCommand {

    private EmployeSA employeSA;

    private EmployeBdl employeBdl;

    private EmployeWsDtoFromDtoFactory employeWsDtoFromDtoFactory;

    private Context context;

    private OperationDto<EmployeDto> employeDtoOperationDto;

    public CreateEmployeCommand(Context context) {
        this.context = context;
        employeSA = EmployeSAImpl_.getInstance_(context);
        employeBdl = EmployeBdlImpl_.getInstance_(context);
        employeWsDtoFromDtoFactory = EmployeWsDtoFromDtoFactoryImpl_.getInstance_(context);
    }

    public void setOperation(OperationDto<EmployeDto> employeDtoOperationDto) {
        this.employeDtoOperationDto = employeDtoOperationDto;
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        EmployeDto data = employeDtoOperationDto.getData();

        Log.d("mahery-haja", "employe wsdto factory " + (employeWsDtoFromDtoFactory == null));

        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);


        employeBdl.create(employeWsDto);
    }

    @Override
    public Long getId() {
        return employeDtoOperationDto.getId();
    }

}
