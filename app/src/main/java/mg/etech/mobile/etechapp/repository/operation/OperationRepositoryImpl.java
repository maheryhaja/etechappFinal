package mg.etech.mobile.etechapp.repository.operation;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import mg.etech.mobile.etechapp.donnee.domainobject.Operation;
import mg.etech.mobile.etechapp.repository.commun.AbstractCommunRepository;

/**
 * Created by mahery.haja on 20/09/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class OperationRepositoryImpl extends AbstractCommunRepository<Operation, Long> implements OperationRepository {
    @RootContext
    protected Context context;

    @AfterInject
    protected void initialize() {
        initDatabase(context);

        if (abstractRunTimeExceptionDao == null) {
            abstractRunTimeExceptionDao = databaseHelper
                    .getRuntimeExceptionDao(Operation.class);
        }
    }
}
