package mg.etech.mobile.etechapp.repository.pole;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.repository.commun.AbstractCommunRepository;

/**
 * Created by mahery.haja on 05/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PoleRepositoryImpl extends AbstractCommunRepository<Pole, Long> implements PoleRepository {

    @RootContext
    protected Context context;

    @AfterInject
    protected void initialize() {
        initDatabase(context);

        if (abstractRunTimeExceptionDao == null) {
            abstractRunTimeExceptionDao = databaseHelper
                    .getRuntimeExceptionDao(Pole.class);
        }
    }
}
