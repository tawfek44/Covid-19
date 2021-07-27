package com.example.covid_19.pojo;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {

    @GET("countries?sort=country")
    Call<List<JsonObject>> getAllCountries();

    @GET("all")
    Call<JsonObject> getTotalCounts();

}
