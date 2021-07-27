package com.example.covid_19.pojo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid_19.pojo.api;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class countryViewModel extends ViewModel {
    private MutableLiveData<List<JsonObject>>countryLiveData=new MutableLiveData<List<JsonObject>>();
    private MutableLiveData<JsonObject>allCountsLiveData=new MutableLiveData<JsonObject>();
    public MutableLiveData<List<JsonObject>> getCountryLiveData() {
        return countryLiveData;
    }
    public void setCountryLiveData(MutableLiveData<List<JsonObject>> countryLiveData) {
        this.countryLiveData = countryLiveData;
    }
    public MutableLiveData<JsonObject> getAllCountsLiveData() {
        return allCountsLiveData;
    }
    public void setAllCountsLiveData(MutableLiveData<JsonObject> allCountsLiveData) {
        this.allCountsLiveData = allCountsLiveData;
    }


    private void getAllCountsData(){
        Retrofit retrofit = client.getRetrofit();
        api server = retrofit.create(api.class);
        Call<JsonObject> call = server.getTotalCounts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                allCountsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    public void getAll(){
        getAllCountsData();
    }

    private void getAllCountriesData(){
        Retrofit retrofit = client.getRetrofit();
        api server = retrofit.create(api.class);
        Call<List<JsonObject>> call = server.getAllCountries();
        call.enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                countryLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {

            }
        });

    }
    public void getCountries(){
        getAllCountriesData();
    }


}
