package mg.etech.mobile.etechapp.service.Metier.employe;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface RetrieveEmployeSM {
    List<Employe> findAll();

    List<Employe> findByPole(Pole pole);
}
