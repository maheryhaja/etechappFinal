package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;
import mg.etech.mobile.etechapp.donnee.wsdto.EmployeWsDto;

/**
 * Created by mahery.haja on 26/09/2017.
 */

public class UpdateEmployeCommand extends BaseEmployeCommand implements OperationCommand {


    public UpdateEmployeCommand(Context context) {
        super(context);
    }

    private boolean anotherUpdateNecessaire = false;

    @Override
    public void execute() throws IOException, ApiCallException {
        // suppose that there are no conflicts
        // execute update and notify for success
        EmployeDto data = employeDtoOperationDto.getData();
        EmployeDto updatedEmployeDto;
        if (employeDtoOperationDto.getData().equalsWithoutPoste(employeDtoOperationDto.getTarget())) {
            // seul une operation d'ajout de poste est necessaire
            // tester pour ajout
            Log.d("mahery-haja", "process add poste");
            EmployeDto target = employeDtoOperationDto.getTarget();
            HistoryPosteDto historyPosteDto = data.getPostes().get(0);


            HistoryPosteDto updatedHistoryPoste = historyPosteDtoFromWsDtoFactory.getInstance(
                    employeBdl.addPoste(
                            historyPosteWsDtoFromDtoFactory.getInstance(historyPosteDto), data.getId()
                    )
            );

            if (target.getPostes().size() > 0) {

                target.getPostes().set(0, updatedHistoryPoste);
            } else
                target.getPostes().add(historyPosteDto);
            updatedEmployeDto = employeDtoOperationDto.getTarget();

        } else {
            // update employe necessaire




        //a recuperer from bdl
        EmployeWsDto employeWsDto = employeWsDtoFromDtoFactory.getInstance(data);
        EmployeWsDto employeWsDtoUpdated = employeBdl.update(employeWsDto);

        PoleDto poleDto = poleSA.findPoleById(employeWsDtoUpdated.getPole());

            updatedEmployeDto = employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDtoUpdated, poleDto);
            employeDtoOperationDto.setData(updatedEmployeDto);
            data.setPhoto(updatedEmployeDto.getPhoto());
            data.setEncodedPhoto(null);
            data.setHiringDate(updatedEmployeDto.getHiringDate());
            data.setBirthDate(updatedEmployeDto.getBirthDate());

            anotherUpdateNecessaire = !updatedEmployeDto.equals(data);

        }

        if (anotherUpdateNecessaire) {
            employeDtoOperationDto.setTarget(updatedEmployeDto);
            employeDtoOperationDto.setData(data);
        }
    }

    @Override
    public Long getId() {

        return employeDtoOperationDto.getId();
    }

    @Override
    public void onSuccess() {

        /***
         * Une operation d'update est considere comme success seulement si data egal source :)
         */

        employeSA.update(employeDtoOperationDto.getData());
        dataBaseSynchroSA.notifyForUpdate(employeDtoOperationDto.getData(), employeDtoOperationDto.getTarget());

        if (anotherUpdateNecessaire) {
            operationStackSynchroSA.updateOperation(employeDtoOperationDto);
        } else {
            operationStackSynchroSA.notifySuccess(employeDtoOperationDto);
        }
    }


}
