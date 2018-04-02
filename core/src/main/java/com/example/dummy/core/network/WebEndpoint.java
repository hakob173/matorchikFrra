package com.example.dummy.core.network;

import com.example.dummy.core.network.cookie.AddCookiesInterceptor;
import com.example.dummy.core.network.cookie.DomainCookie;
import com.example.dummy.core.network.domain.java.IWebEndpointJava;
import com.example.dummy.core.network.domain.java.JavaCookie;
import com.example.dummy.core.network.jackson.JacksonConverterFactory;
import com.example.dummy.core.network.logging.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class WebEndpoint {

    private static final String TAG = "___WebEndpoint";
    private static volatile IWebEndpointJava iWebEndpointJava;

    private WebEndpoint() {

    }

    public static IWebEndpointJava j() {
        if (iWebEndpointJava == null) {
            synchronized (WebEndpoint.class) {
                if (iWebEndpointJava == null) {
                    iWebEndpointJava = (IWebEndpointJava) buildWebEndpointByType(IWebEndpointJava.class, IWebEndpointJava.address, new JavaCookie(), true);
                }
            }
        }
        return iWebEndpointJava;
    }

    private static Object buildWebEndpointByType(
            Class<?> endpointClass, String address, DomainCookie domainCookie,
            boolean addLocaleHeader
    ) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor);

        if (domainCookie != null) {
            builder
                    .addInterceptor(new AddCookiesInterceptor(domainCookie));
        }

        OkHttpClient client = builder
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        client.readTimeoutMillis();

        return new Retrofit.Builder()
                .baseUrl(address)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(endpointClass);


    }


    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

    public static MultipartBody.Part createFilePart(String partName, File file) {
        return MultipartBody.Part.createFormData(partName, file.getName(), RequestBody.create(MediaType.parse("text/plain"), file));
    }
}