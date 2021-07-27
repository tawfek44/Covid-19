package com.example.covid_19.pojo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
             retrofit = new Retrofit.Builder()
                    .baseUrl("https://corona.lmao.ninja/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
