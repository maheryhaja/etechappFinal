package mg.etech.mobile.etechapp.service.applicatif.operation;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.OperationDto;

/**
 * Created by mahery.haja on 21/09/2017.
 */

public interface OperationSA {
    void create(OperationDto operationDto);

    List<OperationDto> findAll();

    void deleteById(Long id);

    void deleteAll();
}
