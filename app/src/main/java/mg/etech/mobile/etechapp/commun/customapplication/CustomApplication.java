package mg.etech.mobile.etechapp.commun.customapplication;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;

import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.database.DataBaseSynchroSAImpl;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.operationStack.OperationStackSynchroSAImpl;
import rx_activity_result2.RxActivityResult;

/**
 * Created by mahery.haja on 04/09/2017.
 */
@EApplication
public class CustomApplication extends Application {

    @Bean(DataBaseSynchroSAImpl.class)
    DataBaseSynchroSA dataBaseSynchroSA;

    @Bean(OperationStackSynchroSAImpl.class)
    OperationStackSynchroSA operationStackSynchroSA;

    @Override
    public void onCreate() {
        super.onCreate();
        RxActivityResult.register(this);
        Stetho.initializeWithDefaults(this);
    }

    @AfterInject
    void initAfterInject() {
        // apres injection des dependances
        Log.d("mahery-haja", "After inject application called");
        dataBaseSynchroSA.initialize();
        operationStackSynchroSA.initialize();
    }
}
