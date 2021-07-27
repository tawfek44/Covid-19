package com.example.covid_19.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;
import com.example.covid_19.pojo.country;
import com.example.covid_19.pojo.countryViewModel;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    countryViewModel vm;

    @BindView(R.id.total_cases_tv)
    TextView totalCasesTv;
    @BindView(R.id.total_recovered)
    TextView totalRecovered;
    @BindView(R.id.total_death)
    TextView totalDeath;
    @BindView(R.id.see_all)
    Button seeAll;

    List<country> countries;
    country currentCountry;
    @BindView(R.id.flag_img)
    ImageView flagImg;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv1)
    TextView totalCasesCurrent;
    @BindView(R.id.tv2)
    TextView cases;
    @BindView(R.id.tv3)
    TextView totalRecoveredCurrent;
    @BindView(R.id.tv4)
    TextView recovered;
    @BindView(R.id.tv5)
    TextView totalDeathCurrent;
    @BindView(R.id.tv6)
    TextView death;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.totalLayout)
    LinearLayout totalLayout;
    @BindView(R.id.recoveredLayout)
    LinearLayout recoveredLayout;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.imageView)
    ImageView cover;

    NumberFormat nf = NumberFormat.getInstance(new Locale(Locale.getDefault().getLanguage(),Locale.getDefault().getCountry()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        seeAll.setOnClickListener(this);
        cardView.setOnClickListener(this);
        getAllData();
    }

    void getAllData() {
        vm = ViewModelProviders.of(this).get(countryViewModel.class);
        vm.getAll();
        vm.getAllCountsLiveData().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject object) {
                int cs = Integer.parseInt(object.get("cases").toString());
                int rc = Integer.parseInt(object.get("recovered").toString());
                int dt = Integer.parseInt(object.get("deaths").toString());

                ValueAnimator animatorCs = ValueAnimator.ofInt(1, cs); //0 is min number, 600 is max number
                ValueAnimator animatorRc = ValueAnimator.ofInt(1, rc); //0 is min number, 600 is max number
                ValueAnimator animatorDt = ValueAnimator.ofInt(1, dt); //0 is min number, 600 is max number
                animatorCs.setDuration(3000); //Duration is in milliseconds
                animatorRc.setDuration(3000); //Duration is in milliseconds
                animatorDt.setDuration(3000); //Duration is in milliseconds
                animatorCs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        totalCasesTv.setText(nf.format(Integer.parseInt(animation.getAnimatedValue().toString())));
                    }
                });
                animatorRc.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        totalRecovered.setText(nf.format(Integer.parseInt(animation.getAnimatedValue().toString())));
                    }
                });
                animatorDt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        totalDeath.setText(nf.format(Integer.parseInt(animation.getAnimatedValue().toString())));
                    }
                });
                animatorCs.start();
                animatorRc.start();
                animatorDt.start();
            }
        });

        vm.getCountries();
        vm.getCountryLiveData().observe(this, new Observer<List<JsonObject>>() {
            @Override
            public void onChanged(List<JsonObject> jsonObjects) {
                country count;
                TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                String reg = tm.getNetworkCountryIso();
                countries = new ArrayList<>();
                for (int i = 0; i < jsonObjects.size(); i++) {
                    count = new country();
                    count.setCountry(jsonObjects.get(i).get("country").toString().replace("\"", ""));
                    count.setFlag(((JsonObject) jsonObjects.get(i).get("countryInfo")).get("flag").toString().replace("\"", ""));
                    count.setTotalCases(jsonObjects.get(i).get("cases").toString());
                    count.setCases(jsonObjects.get(i).get("todayCases").toString());
                    count.setTotalDeath(jsonObjects.get(i).get("deaths").toString());
                    count.setDeath(jsonObjects.get(i).get("todayDeaths").toString());
                    count.setTotalRecovered(jsonObjects.get(i).get("recovered").toString());
                    count.setRecovered(jsonObjects.get(i).get("todayRecovered").toString());
                    countries.add(count);
                    if (count.getCountry().toLowerCase().startsWith(reg.toLowerCase())) {
                        currentCountry = count;
                        setCurrentCountry();
                    }
                }
                Collections.reverse(countries);
            }
        });
    }

    void setCurrentCountry() {
        Glide.with(this).load(currentCountry.getFlag()).into(flagImg);
        name.setText(currentCountry.getCountry());
        totalCasesCurrent.setText(getResources().getString(R.string.total_cases_lbl)+ nf.format(Integer.parseInt(currentCountry.getTotalCases())));
        cases.setText(getResources().getString(R.string.today_s_cases_lbl)+ nf.format(Integer.parseInt(currentCountry.getCases())));
        totalRecoveredCurrent.setText(getResources().getString(R.string.total_recovered_lbl)+ nf.format(Integer.parseInt(currentCountry.getTotalRecovered())));
        recovered.setText(getResources().getString(R.string.today_s_recovered_lbl)+ nf.format(Integer.parseInt(currentCountry.getRecovered())));
        totalDeathCurrent.setText(getResources().getString(R.string.total_death_lbl)+ nf.format(Integer.parseInt(currentCountry.getTotalDeath())));
        death.setText(getResources().getString(R.string.today_death_lbl) + nf.format(Integer.parseInt(currentCountry.getDeath())));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.see_all) {
            Intent intent = new Intent(MainActivity.this, countriesActivity.class);
            intent.putExtra("countries", (Serializable) countries);
            startActivity(intent);
        }
        else if(id == R.id.card_view){
            if(name.getVisibility() == View.GONE){
                name.setVisibility(View.VISIBLE);
                totalDeathCurrent.setVisibility(View.VISIBLE);
                death.setVisibility(View.VISIBLE);
                totalRecoveredCurrent.setVisibility(View.VISIBLE);
                recovered.setVisibility(View.VISIBLE);
                totalCasesCurrent.setVisibility(View.VISIBLE);
                cases.setVisibility(View.VISIBLE);
                cover.setVisibility(View.VISIBLE);
            }
            else{
                name.setVisibility(View.GONE);
                totalDeathCurrent.setVisibility(View.GONE);
                death.setVisibility(View.GONE);
                totalRecoveredCurrent.setVisibility(View.GONE);
                recovered.setVisibility(View.GONE);
                totalCasesCurrent.setVisibility(View.GONE);
                cases.setVisibility(View.GONE);
                cover.setVisibility(View.GONE);
            }
        }
    }
}