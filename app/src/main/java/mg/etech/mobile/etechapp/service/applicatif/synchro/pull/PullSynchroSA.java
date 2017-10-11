package mg.etech.mobile.etechapp.service.applicatif.synchro.pull;

import io.reactivex.Observable;

/**
 * Created by mahery.haja on 25/09/2017.
 */

public interface PullSynchroSA {
    void launch();

    Observable<Boolean> getRunningObservable();
}
