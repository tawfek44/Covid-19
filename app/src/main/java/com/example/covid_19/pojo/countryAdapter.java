package com.example.covid_19.pojo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid_19.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class countryAdapter extends RecyclerView.Adapter<countryAdapter.countryViewHolder> {

    private List<country>countries;
    private Context context;
    @NonNull
    @Override
    public countryAdapter.countryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new countryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull countryAdapter.countryViewHolder holder, int position) {
        Glide.with(context).load(countries.get(position).getFlag()).into(holder.flag);
        if(countries.get(position).isVisible()){
            holder.name.setVisibility(View.VISIBLE);
            holder.totalDeath.setVisibility(View.VISIBLE);
            holder.death.setVisibility(View.VISIBLE);
            holder.totalRecovered.setVisibility(View.VISIBLE);
            holder.recovered.setVisibility(View.VISIBLE);
            holder.totalCases.setVisibility(View.VISIBLE);
            holder.cases.setVisibility(View.VISIBLE);
            holder.cover.setVisibility(View.VISIBLE);
        }
        else{
            holder.name.setVisibility(View.GONE);
            holder.totalDeath.setVisibility(View.GONE);
            holder.death.setVisibility(View.GONE);
            holder.totalRecovered.setVisibility(View.GONE);
            holder.recovered.setVisibility(View.GONE);
            holder.totalCases.setVisibility(View.GONE);
            holder.cases.setVisibility(View.GONE);
            holder.cover.setVisibility(View.GONE);
        }
        NumberFormat nf = NumberFormat.getInstance(new Locale(Locale.getDefault().getLanguage(),Locale.getDefault().getCountry()));
        holder.name.setText(countries.get(position).getCountry());
        holder.totalCases.setText(context.getResources().getString(R.string.total_cases_lbl)+ nf.format(Integer.parseInt(countries.get(position).getTotalCases())));
        holder.cases.setText(context.getResources().getString(R.string.today_s_cases_lbl)+ nf.format(Integer.parseInt(countries.get(position).getCases())));
        holder.totalRecovered.setText(context.getResources().getString(R.string.total_recovered_lbl)+ nf.format(Integer.parseInt(countries.get(position).getTotalRecovered())));
        holder.recovered.setText(context.getResources().getString(R.string.today_s_recovered_lbl)+ nf.format(Integer.parseInt(countries.get(position).getRecovered())));
        holder.totalDeath.setText(context.getResources().getString(R.string.total_death_lbl)+ nf.format(Integer.parseInt(countries.get(position).getTotalDeath())));
        holder.death.setText(context.getResources().getString(R.string.today_death_lbl) + nf.format(Integer.parseInt(countries.get(position).getDeath())));
    }

    @Override
    public int getItemCount() {
        if(countries == null)
            return 0;
        return countries.size();
    }

    public List<country> getCountries() {
        return countries;
    }

    public void setCountries(List<country> countries) {
        this.countries = countries;
    }


    class countryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView flag,cover;
        TextView name,totalCases,cases,totalDeath,death,totalRecovered,recovered;
        public countryViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag_img);
            name = itemView.findViewById(R.id.name);
            totalCases = itemView.findViewById(R.id.tv1);
            cases = itemView.findViewById(R.id.tv2);
            totalRecovered = itemView.findViewById(R.id.tv3);
            recovered = itemView.findViewById(R.id.tv4);
            totalDeath = itemView.findViewById(R.id.tv5);
            death = itemView.findViewById(R.id.tv6);
            cover = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!countries.get(getLayoutPosition()).isVisible()){
                name.setVisibility(View.VISIBLE);
                totalDeath.setVisibility(View.VISIBLE);
                death.setVisibility(View.VISIBLE);
                totalRecovered.setVisibility(View.VISIBLE);
                recovered.setVisibility(View.VISIBLE);
                totalCases.setVisibility(View.VISIBLE);
                cases.setVisibility(View.VISIBLE);
                cover.setVisibility(View.VISIBLE);
                countries.get(getLayoutPosition()).setVisible(true);
            }
            else{
                name.setVisibility(View.GONE);
                totalDeath.setVisibility(View.GONE);
                death.setVisibility(View.GONE);
                totalRecovered.setVisibility(View.GONE);
                recovered.setVisibility(View.GONE);
                totalCases.setVisibility(View.GONE);
                cases.setVisibility(View.GONE);
                cover.setVisibility(View.GONE);
                countries.get(getLayoutPosition()).setVisible(false);
            }
        }
    }
}
