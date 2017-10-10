package mg.etech.mobile.etechapp.service.applicatif;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.constante.Session;
import mg.etech.mobile.etechapp.commun.utils.preference.PreferencesSingleton;
import mg.etech.mobile.etechapp.service.applicatif.preferences.PreferenceSA;

/**
 * Created by mahery.haja on 29/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class PreferenceSAImpl implements PreferenceSA {

    @Bean
    PreferencesSingleton preferences;

    @Override
    public boolean isUserConnected() {
        return preferences.getBool(Session.IS_CONNECTED_PREF);
    }

    @Override
    public void setUserConnected(boolean isConnected) {
        preferences.setBool(Session.IS_CONNECTED_PREF, isConnected);
    }


    //default value false
    @Override
    public boolean isFirstTimeLaunched() {
        return !preferences.getBool(Session.IS_FIRST_TIME_LAUNCHED);
    }

    @Override
    public void setFirstTimeLaunched(Boolean isFirstTimeLaunched) {
        preferences.setBool(Session.IS_FIRST_TIME_LAUNCHED, !isFirstTimeLaunched);
    }
}
