package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactoryImpl;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean
public class CreateEmployeCommand implements OperationCommand {

    @Bean(EmployeSAImpl.class)
    EmployeSA employeSA;

    @Bean(EmployeBdlImpl.class)
    EmployeBdl employeBdl;

    @Bean(EmployeWsDtoFromDtoFactoryImpl.class)
    EmployeWsDtoFromDtoFactory employeWsDtoFromDtoFactory;

    private OperationDto<EmployeDto> employeDtoOperationDto;


    public void setOperation(OperationDto<EmployeDto> employeDtoOperationDto) {
        this.employeDtoOperationDto = employeDtoOperationDto;
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        employeBdl.create(employeWsDtoFromDtoFactory.getInstance(employeDtoOperationDto.getData()));
    }

    @Override
    public Long getId() {
        return employeDtoOperationDto.getId();
    }

}
