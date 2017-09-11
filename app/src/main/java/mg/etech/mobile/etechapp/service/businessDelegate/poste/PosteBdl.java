package mg.etech.mobile.etechapp.service.businessDelegate.poste;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.donnee.wsdto.PosteWsDto;

/**
 * Created by mahery.haja on 11/09/2017.
 */
public interface PosteBdl {
    List<PosteWsDto> findAll() throws IOException;
}
