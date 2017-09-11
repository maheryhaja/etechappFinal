package mg.etech.mobile.etechapp.repository.employe;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.donnee.domainobject.Employe;
import mg.etech.mobile.etechapp.donnee.domainobject.Pole;
import mg.etech.mobile.etechapp.repository.commun.AbstractCommunRepository;

/**
 * Created by maheryHaja on 9/11/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EmployeRepositoryImpl extends AbstractCommunRepository<Employe, Long> implements EmployeRepository {
    @RootContext
    protected Context context;

    @AfterInject
    protected void initialize() {
        initDatabase(context);

        if (abstractRunTimeExceptionDao == null) {
            abstractRunTimeExceptionDao = databaseHelper
                    .getRuntimeExceptionDao(Employe.class);
        }
    }
}
