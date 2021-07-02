package com.fitnessclub.Networking;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl("http://getfitt.club/")
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static GymApi gymApi = retrofit.create(GymApi.class);

    public static GymApi getGymApi() {
        return gymApi;
    }

}
