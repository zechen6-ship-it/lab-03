package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    public void addCity(City city){
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    public void updateCity(City city, int position) {
        if (position >= 0 && position < cityAdapter.getCount()) {
            cityAdapter.remove(cityAdapter.getItem(position));
            cityAdapter.insert(city, position);
            cityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        cityList = findViewById(R.id.city_list);
        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City selectedCity = (City) adapterView.getItemAtPosition(i);
                AddCityFragment fragment = AddCityFragment.newInstance(
                        selectedCity.getName(),
                        selectedCity.getProvince(),
                        i
                );
                fragment.show(getSupportFragmentManager(), "Edit City");
            }
        });

    }
}