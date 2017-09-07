package mg.etech.mobile.etechapp.service.businessDelegate.pole;

import java.io.IOException;
import java.util.List;

import mg.etech.mobile.etechapp.commun.exception.commun.ApiCallException;
import mg.etech.mobile.etechapp.donnee.wsdto.PoleWsDto;

/**
 * Created by mahery.haja on 06/09/2017.
 */
public interface PoleBdl {
    List<PoleWsDto> findAll() throws IOException, ApiCallException;
}
