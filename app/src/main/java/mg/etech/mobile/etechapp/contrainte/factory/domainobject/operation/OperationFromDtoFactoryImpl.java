package mg.etech.mobile.etechapp.contrainte.factory.domainobject.operation;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.EBean;

import java.util.Collection;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationFromDtoFactoryImpl implements OperationFromDtoFactory {
    @Override
    public Operation getInstance() {


        return null;
    }

    @Override
    public Operation getInstance(OperationDto operationDto) {

        ObjectMapper objectMapper = new ObjectMapper();

        Operation operation = new Operation();
        operation.setId(operationDto.getId());
        operation.setOperationName(operationDto.getOperationName());
        String className = operationDto.getData().getClass().toString();
        operation.setClassName(className);
        Log.d("mahery-haja", "className found " + className + " id" + operation.getId());
        try {
            operation.setData(objectMapper.writeValueAsString(operationDto.getData()));
            operation.setTarget(objectMapper.writeValueAsString(operationDto.getTarget()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return operation;
    }

    @Override
    public List<Operation> getInstance(Collection<OperationDto> dObjs) {
        return null;
    }
}
