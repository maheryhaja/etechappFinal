package mg.etech.mobile.etechapp.service.applicatif.operation.commands;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        // data represente la modification finale a atteindre
        EmployeDto data = employeDtoOperationDto.getData();

        // target represente l'etat actuel
        EmployeDto target = employeDtoOperationDto.getTarget();
        EmployeDto updatedEmployeDto;

        if (employeDtoOperationDto.getData().equalsWithoutPoste(employeDtoOperationDto.getTarget())) {

            //gather all negative element from data
            List<HistoryPosteDto> negativeHistory = new ArrayList<>();
            for (HistoryPosteDto historyPosteDto : data.getPostes()) {
                if (historyPosteDto.getId() < 0) {
                    negativeHistory.add(historyPosteDto);
                }
            }

            if (!negativeHistory.isEmpty()) {
                // ajout necessaire
                // prendre le premier element a ajouter
                HistoryPosteDto historyPosteDtoRequest = negativeHistory.get(0);


                HistoryPosteDto historyPosteResponse = historyPosteDtoFromWsDtoFactory.getInstance(
                        employeBdl.addPoste(
                                historyPosteWsDtoFromDtoFactory.getInstance(historyPosteDtoRequest), data.getId()
                        )
                );

                Log.d("mahery-haja", "need to add " + historyPosteResponse.getName() + " " + historyPosteResponse.getDatePromotion());
                //data.getPostes().set(0, historyPosteResponse);

//                if (target.getPostes().size() > 0) {
//
//                    target.getPostes().set(0, historyPosteResponse);
//                } else
                target.getPostes().add(historyPosteResponse);
                //actualiser id
                historyPosteDtoRequest.setId(historyPosteResponse.getId());
                historyPosteDtoRequest.setDatePromotion(historyPosteResponse.getDatePromotion());

            }
            updatedEmployeDto = employeDtoOperationDto.getTarget();

            employeDtoOperationDto.setData(data);
            employeDtoOperationDto.setTarget(target);

        } else {
            // update employe necessaire
            Log.d("mahery-haja", "command process simple update");


            //make postes transparent



        //a recuperer from bdl
            EmployeWsDto employeWsDtoRequest = employeWsDtoFromDtoFactory.getInstance(data);
            EmployeWsDto employeWsDtoResponse = employeBdl.update(employeWsDtoRequest);

            PoleDto poleDto = poleSA.findPoleById(employeWsDtoResponse.getPole());

            updatedEmployeDto = employeDtoFromWsDtoFactory.getInstanceWithPoleDto(employeWsDtoResponse, poleDto);

            updatedEmployeDto.setPostes(target.getPostes());

            data.setPhoto(updatedEmployeDto.getPhoto());
            data.setEncodedPhoto(null);
            data.setHiringDate(updatedEmployeDto.getHiringDate());
            data.setBirthDate(updatedEmployeDto.getBirthDate());

            updatedEmployeDto.setPostes(target.getPostes());
            employeDtoOperationDto.setTarget(updatedEmployeDto);
            //actualiser target


        }

        anotherUpdateNecessaire = !employeDtoOperationDto.getTarget().equals(employeDtoOperationDto.getData());

        if (anotherUpdateNecessaire) {

            Log.d("mahery-haja", "update: another update necessaire");
            //une autre update necessaire jusq'a target ressemble a data

            // target represente l'etat actuel
            employeDtoOperationDto.setTarget(updatedEmployeDto);
            // data represente l'etat a atteindre
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
