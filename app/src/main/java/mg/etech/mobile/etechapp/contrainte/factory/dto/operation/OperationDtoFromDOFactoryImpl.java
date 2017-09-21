package mg.etech.mobile.etechapp.contrainte.factory.dto.operation;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationDtoFromDOFactoryImpl implements OperationDtoFromDOFactory {
    @Override
    public OperationDto getInstance() {
        return null;
    }

    @Override
    public OperationDto getInstance(Operation operation) {

        //need to be polpulated as needed
        String classname = operation.getClassName();
        ObjectMapper objectMapper = new ObjectMapper();
        if (classname.contains(EmployeDto.class.getName())) {
            OperationDto<EmployeDto> employeDtoOperationDto = new OperationDto<>();
            employeDtoOperationDto.setId(operation.getId());
            employeDtoOperationDto.setOperationName(operation.getOperationName());
            employeDtoOperationDto.setClassName(classname);
            try {
                employeDtoOperationDto.setData(objectMapper.readValue(operation.getData(), EmployeDto.class));
                employeDtoOperationDto.setTarget(objectMapper.readValue(operation.getTarget(), EmployeDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return employeDtoOperationDto;
        } else {
            Log.d("mahery-haja", "no classname that match " + EmployeDto.class.getName());
            return null;
        }
    }

    @Override
    public List<OperationDto> getInstance(Collection<Operation> dObjs) {
        return null;
    }
}
