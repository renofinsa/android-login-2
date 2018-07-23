package com.renofinsa.belajarconcept.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper class untuk mendapatkan retrofit service
 *
 * @author hendrawd on 7/31/17
 */

public class ServiceGenerator {
    public static final String YOUTUBE_API_KEY = "AIzaSyCWivACXwDuzEJJEYDi4ZiDDFgU7_aDRaE";
    private static final String BASE_URL = "http://192.168.109.118/belajarConcept/webservice/";
    public static final String PHOTO_PROFILE_PATH = "https://hj123.whitaaplikasi.com/photo_profile/";

    private static OkHttpClient.Builder sHttpClient =
            new OkHttpClient.Builder()
                    .addInterceptor(new apiKeyAdapter());

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(sHttpClient.build());

    public static <T> T createService(Class<T> serviceClass) {
        return sBuilder.build().create(serviceClass);
    }
}
