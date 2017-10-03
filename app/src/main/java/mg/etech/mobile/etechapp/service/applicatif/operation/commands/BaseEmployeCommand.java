package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;

import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.employe.EmployeDtoFromWsDtoFactoryImpl_;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.HistoryPosteDtoFromWsDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.dto.poste.HistoryPosteDtoFromWsDtoFactoryImpl_;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.employe.EmployeWsDtoFromDtoFactoryImpl_;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactory;
import mg.etech.mobile.etechapp.contrainte.factory.wsdto.poste.HistoryPosteWsDtoFromDtoFactoryImpl_;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSA;
import mg.etech.mobile.etechapp.service.applicatif.employe.EmployeSAImpl_;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSA;
import mg.etech.mobile.etechapp.service.applicatif.pole.PoleSAImpl_;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl_;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl_;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdl;
import mg.etech.mobile.etechapp.service.businessDelegate.employe.EmployeBdlImpl_;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class BaseEmployeCommand {
    protected EmployeSA employeSA;
    protected EmployeBdl employeBdl;
    protected EmployeWsDtoFromDtoFactory employeWsDtoFromDtoFactory;
    protected EmployeDtoFromWsDtoFactory employeDtoFromWsDtoFactory;

    protected HistoryPosteWsDtoFromDtoFactory historyPosteWsDtoFromDtoFactory;
    protected HistoryPosteDtoFromWsDtoFactory historyPosteDtoFromWsDtoFactory;

    protected Context context;
    protected OperationDto<EmployeDto> employeDtoOperationDto;
    protected DataBaseSynchroSA dataBaseSynchroSA;
    protected PoleSA poleSA;
    protected OperationStackSynchroSA operationStackSynchroSA;

    public BaseEmployeCommand(Context context) {
        this.context = context;
        employeBdl = EmployeBdlImpl_.getInstance_(context);
        employeWsDtoFromDtoFactory = EmployeWsDtoFromDtoFactoryImpl_.getInstance_(context);
        employeDtoFromWsDtoFactory = EmployeDtoFromWsDtoFactoryImpl_.getInstance_(context);

        historyPosteDtoFromWsDtoFactory = HistoryPosteDtoFromWsDtoFactoryImpl_.getInstance_(context);
        historyPosteWsDtoFromDtoFactory = HistoryPosteWsDtoFromDtoFactoryImpl_.getInstance_(context);

        employeSA = EmployeSAImpl_.getInstance_(context);
        poleSA = PoleSAImpl_.getInstance_(context);
        dataBaseSynchroSA = DataBaseSynchroSAImpl_.getInstance_(context);
        operationStackSynchroSA = OperationStackSynchroSAImpl_.getInstance_(context);
    }

    public void setOperation(OperationDto<EmployeDto> employeDtoOperationDto) {
        this.employeDtoOperationDto = employeDtoOperationDto;
    }
}
