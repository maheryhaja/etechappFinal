package mg.etech.mobile.etechapp.service.Metier.employe;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Employe;

/**
 * Created by mahery.haja on 07/09/2017.
 */
public interface CreateUpdateDeleteEmployeSM {
    void create(Employe employe);


    void create(List<Employe> employes);

    void deleteById(Long id);

    void update(Employe employe);

    void deleteAll();
}
