package mg.etech.mobile.etechapp.contrainte.singleton;


import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.androidannotations.annotations.EBean;

import mg.etech.mobile.etechapp.commun.config.ConfigUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by mahery.haja on 31/08/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrofitSingletonImpl implements RetrofitSingleton {
    @Override
    public Retrofit getDefaultForRx() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit
                .Builder()
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                //   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConfigUrl.BASE_URL)
                .build();
    }
}
