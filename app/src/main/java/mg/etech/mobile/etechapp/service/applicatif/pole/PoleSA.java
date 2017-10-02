package mg.etech.mobile.etechapp.service.applicatif.pole;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.PoleDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
public interface PoleSA {
    List<PoleDto> findAll();

    void create(PoleDto poleDto);

    void create(List<PoleDto> poleDtos);

    PoleDto findPoleById(Long id);

    void deleteAll();
}

