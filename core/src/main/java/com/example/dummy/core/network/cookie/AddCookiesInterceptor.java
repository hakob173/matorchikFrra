
package com.example.dummy.core.network.cookie;

import com.example.dummy.core.storage.StorageEngine;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    private static final String TAG = "___AddCookiesInterceptor";
    private DomainCookie cookieDomain;

    public AddCookiesInterceptor(DomainCookie cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        Object cookie = StorageEngine.read(cookieDomain.getCookie());
        if (cookie != null && cookie instanceof String) {
//            Platform.get().log(6, TAG + ":Cookie=" + cookieDomain.getCookieHeader() + "=" + cookie, null);
            builder.addHeader("Cookie", cookieDomain.getCookieHeader() + "=" + cookie);
        }

        return chain.proceed(builder.build());
    }
}