package mg.etech.mobile.etechapp.service.Metier.pole;

import java.util.List;

import mg.etech.mobile.etechapp.donnee.domainobject.Pole;

/**
 * Created by mahery.haja on 06/09/2017.
 */
public interface CreateUpdateDeletePoleSM {
    void create(Pole pole);

    void create(List<Pole> poleList);
    void deleteAll();
}
