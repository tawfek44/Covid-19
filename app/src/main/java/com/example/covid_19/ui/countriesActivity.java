package com.example.covid_19.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import com.example.covid_19.pojo.country;
import java.util.List;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.pojo.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.pojo.country;
import com.example.covid_19.pojo.countryViewModel;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class countriesActivity extends AppCompatActivity {

    List<country>countries;
    countryAdapter adapter;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.rec)
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        ButterKnife.bind(this);
        prepareRecyclerView();
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<country>found=new ArrayList<>();
                for(int i=0;i<countries.size();i++){
                    if(countries.get(i).getCountry().toLowerCase().contains(s.toString().toLowerCase()))
                        found.add(countries.get(i));
                }
                adapter = new countryAdapter();
                adapter.setCountries(found);
                rec.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void prepareRecyclerView(){
        countries = (List<country>) getIntent().getSerializableExtra("countries");
        countryAdapter adapter = new countryAdapter();
        adapter.setCountries(countries);
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);
    }
}