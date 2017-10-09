package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.commun.simpleserializer.OperationType;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 20/09/2017.
 */
public class CreateEmployeCommand extends BaseEmployeCommand implements OperationCommand {

    private boolean editPosteNecessaire = false;

    public CreateEmployeCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() throws IOException, ApiCallException {
        // execute create and notify success

        Log.d("mahery-haja", "process Operation id " + employeDtoOperationDto.getId());
        EmployeDto data = employeDtoOperationDto.getData();

        Log.d("mahery-haja", "employe wsdto factory " + (employeWsDtoFromDtoFactory == null));

        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);
//        employeWsDto.setPostes(null);


        EmployeDto created = employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeBdl.create(employeWsDto), data.getPole());


        // created correspond a employe cree sans les postes
        employeDtoOperationDto.setData(created);
        editPosteNecessaire = data.getPostes().size() > 0;
//        editPosteNecessaire = false;
        if (editPosteNecessaire) {
            employeDtoOperationDto.setTarget(created);
            data.setId(created.getId());
            data.setBirthDate(created.getBirthDate());
            data.setHiringDate(created.getHiringDate());

            employeDtoOperationDto.setData(data);
        }
    }

    @Override
    public void onSuccess() {
        // create the employe in database
        employeSA.create(employeDtoOperationDto.getData());

        // notify databasynchro to update its employeDto map
        dataBaseSynchroSA.notifyForCreate(employeDtoOperationDto.getData());

        if (editPosteNecessaire) {
            //changer operation en update
            employeDtoOperationDto.setOperationName(OperationType.UPDATE);

            operationStackSynchroSA.updateOperation(employeDtoOperationDto);
        } else {
            // operation habituel
            // notifier operation Stack pour le success d'une operation
            operationStackSynchroSA.notifySuccess(employeDtoOperationDto);
        }


    }

    @Override
    public Long getId() {
        return employeDtoOperationDto.getId();
    }

}
