package mg.etech.mobile.etechapp.commun.customapplication;

import android.app.Application;

import com.facebook.stetho.Stetho;

import rx_activity_result2.RxActivityResult;

/**
 * Created by mahery.haja on 04/09/2017.
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxActivityResult.register(this);
        Stetho.initializeWithDefaults(this);
    }
}
