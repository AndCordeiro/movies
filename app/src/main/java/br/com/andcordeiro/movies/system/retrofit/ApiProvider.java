package br.com.andcordeiro.movies.system.retrofit;

import android.content.Context;

import br.com.andcordeiro.movies.system.Consts;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiProvider {

    private Api api;
    private Context context;
    private String url = Consts.Url.URL_DEFAULT;

    public ApiProvider(Context context) {
        this.context = context;
        this.api = buildApi();
    }

    private Api buildApi() {
        return build(Api.class, url);
    }

    private <T> T build(final Class<T> apiClass, String apiHost) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addNetworkInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);

        try {
            return new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(apiHost)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(apiClass);
        } catch (IllegalArgumentException i) {
            i.printStackTrace();
            return null;
        }
    }

    public Api getApi() {
        return api;
    }

}
