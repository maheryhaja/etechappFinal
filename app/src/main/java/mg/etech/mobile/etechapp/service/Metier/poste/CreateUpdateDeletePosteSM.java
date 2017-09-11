package mg.etech.mobile.etechapp.service.Metier.poste;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Poste;

/**
 * Created by maheryHaja on 9/11/2017.
 */

public interface CreateUpdateDeletePosteSM {
    void create(Poste poste);

    void deleteAll();

    void create(List<Poste> postes);
}
