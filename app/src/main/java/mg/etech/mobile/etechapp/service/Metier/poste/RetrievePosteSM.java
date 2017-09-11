package mg.etech.mobile.etechapp.service.Metier.poste;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Poste;

/**
 * Created by mahery.haja on 11/09/2017.
 */
public interface RetrievePosteSM {
    List<Poste> findAll();
}
