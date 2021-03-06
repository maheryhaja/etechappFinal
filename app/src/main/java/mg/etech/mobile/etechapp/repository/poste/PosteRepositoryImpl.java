package mg.etech.mobile.etechapp.repository.poste;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.donnee.domainobject.Poste;
import mg.etech.mobile.etechapp.repository.commun.AbstractCommunRepository;

/**
 * Created by mahery.haja on 11/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PosteRepositoryImpl extends AbstractCommunRepository<Poste, Long> implements PosteRepository {
    @RootContext
    protected Context context;

    @AfterInject
    protected void initialize() {
        initDatabase(context);

        if (abstractRunTimeExceptionDao == null) {
            abstractRunTimeExceptionDao = databaseHelper
                    .getRuntimeExceptionDao(Poste.class);
        }
    }

}
