package com.example.julian_lelang.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String Base_url = "http://192.168.43.236:80/api/";
    public static String Base_url_files = "http://192.168.43.236:80/images/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String BaseUrl){
        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client).build();
        }
        return retrofit;
    }
    public static ApiService getApiService(){
        return ApiClient.getClient(Base_url).create(ApiService.class);
    }
}
