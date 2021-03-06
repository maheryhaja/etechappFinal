package mg.etech.mobile.etechapp.service.applicatif.employe;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.PoleDto;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface EmployeSA {

    List<EmployeDto> findAll();

    EmployeDto findById(Long id);

    void create(EmployeDto employeDto);

    void create(List<EmployeDto> employeDtos);

    void deleteById(Long id);

    List<EmployeDto> findByPole(PoleDto poleDto);

    void deleteAll();

    void update(EmployeDto employeDto);
}
