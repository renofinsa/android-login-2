package com.renofinsa.belajarconcept.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor yang digunakan untuk menambahkan API KEY secara dynamic saat request
 *
 * @author hendrawd on 7/31/17
 */

public class apiKeyAdapter implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final HttpUrl url = chain.request()
                .url()
                .newBuilder()
                // .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API)
                .build();
        final Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
