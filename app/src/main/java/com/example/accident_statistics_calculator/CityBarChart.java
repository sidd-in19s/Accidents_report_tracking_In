package com.example.accident_statistics_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CityBarChart extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private BarChart barChart;
    private String selectedState;
    private String selectedDistrict;
    private ArrayList<String> cities = new ArrayList<>();
    private ArrayList<Integer> counts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_barchart);

        // Get the selected state and district from the intent extras
        selectedState = getIntent().getStringExtra("selectedState");
        selectedDistrict = getIntent().getStringExtra("selectedDistrict");

        barChart = findViewById(R.id.city_barChart);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("accident_reports");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String state = snapshot.child("State").getValue(String.class);
                String district = snapshot.child("District").getValue(String.class);
                String city_or_vlg = snapshot.child("City_OR_Village").getValue(String.class);
                if (district.equals(selectedDistrict)) {
                    int index = cities.indexOf(city_or_vlg);
                    if (index == -1) {
                        cities.add(city_or_vlg);
                        counts.add(1);
                    } else {
                        counts.set(index, counts.get(index) + 1);
                    }
                    updateChart();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Do nothing
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Do nothing
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Do nothing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

        // Add an OnChartValueSelectedListener to the bar chart
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Retrieve the selected city
                String selectedCity = cities.get((int) e.getX());

                // Pass the selected state, district, and city to a new activity
                Intent intent = new Intent(CityBarChart.this, CausePieChart.class);
                intent.putExtra("selectedState", selectedState);
                intent.putExtra("selectedDistrict", selectedDistrict);
                intent.putExtra("selectedCity", selectedCity);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
                // Do nothing
            }
        });
    }

    private void updateChart() {
        ArrayList city_barchart_arr = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            city_barchart_arr.add(new BarEntry(i, counts.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(city_barchart_arr, "Road Accidents in " + selectedState + " cities");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText(selectedState + " District Accident Counts Bar Chart");
        barChart.animateY(2000);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(cities));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.invalidate();
    }
}