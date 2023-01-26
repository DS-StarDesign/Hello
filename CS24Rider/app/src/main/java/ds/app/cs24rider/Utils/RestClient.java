package ds.app.cs24rider.Utils;

import static ds.app.cs24rider.Utils.Constance.BASE_URL;
import static ds.app.cs24rider.Utils.Constance.MAP_API_URL;
import static ds.app.cs24rider.Utils.PrefManager.sPrefManagerName;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
 *  Created By MD. OLI ULLAH DEWAN 06-01-2023
 *  DEWAN SOFTWARE LTD.
 * */
public class RestClient {
    private Context mContext;

    public static Retrofit getClient(Context context, boolean apiOption) {
        String URL = "";
        if(apiOption){
            URL = BASE_URL;
        }else{
            URL = MAP_API_URL;
        }
        String token = PrefManager.getInstance(context).getString(PrefManager.ACCESS_TOKEN);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder().setLenient().create();

        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.SSL_3_0)
                .allEnabledCipherSuites()
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            if(URL.contains("https")){
                client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                return chain.proceed(original.newBuilder()
                                        .header("Authorization", token == null || token.isEmpty() ? sPrefManagerName : token)
                                        .header("site-name", "app")
                                        .method(original.method(), original.body())
                                        .build());
                            }
                        })
                        .addNetworkInterceptor(new StethoInterceptor())
                        .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1, Protocol.SPDY_3))
                        //.sslSocketFactory(new TLSSocketCertificate().getSSLSocketFactory())
                        .connectionSpecs(Collections.singletonList(spec))
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .writeTimeout(300, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();
            }else{
                client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                return chain.proceed(original.newBuilder()
                                        .header("Authorization", token == null || token.isEmpty() ? sPrefManagerName : token)
                                        .header("site-name", "app")
                                        .method(original.method(), original.body())
                                        .build());
                            }
                        })
                        .addNetworkInterceptor(new StethoInterceptor())
                        .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1, Protocol.SPDY_3))
                        //.sslSocketFactory(new TLSSocketCertificate().getSSLSocketFactory())
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .writeTimeout(300, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
    }

    public static <T> T getService(Context context, Class<T> serviceClass, boolean api_option) {
        return getClient(context, api_option).create(serviceClass);
    }
}
