package mg.etech.mobile.etechapp.service.applicatif.poste;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.dto.PosteDto;

/**
 * Created by mahery.haja on 11/09/2017.
 */
public interface PosteSA {
    List<PosteDto> findAll();

    void create(PosteDto posteDto);

    void create(List<PosteDto> posteDtos);

    void deleteAll();

}
