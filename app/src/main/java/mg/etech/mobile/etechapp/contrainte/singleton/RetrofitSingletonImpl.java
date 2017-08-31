package mg.etech.mobile.etechapp.contrainte.singleton;


import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrofitSingletonImpl implements RetrofitSingleton {
    @Override
    public Retrofit getDefaultForRx() {

        return new Retrofit
                .Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConfigUrl.BASE_URL)
                .build();
    }
}
