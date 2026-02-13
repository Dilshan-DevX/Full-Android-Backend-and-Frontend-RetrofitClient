package com.example.netpractical.client;

import android.content.Context;

import com.example.netpractical.intercepter.Authinercepter;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static final String BASE_URL = "http://10.0.2.2:8080/api/v1/";
    ///emulaters
    ///Local Device => Ipv4 address of device

    public static  Retrofit getInstanse(Context context){
        if(retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Authinercepter(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
