package com.example.accident_statistics_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CausePieChart extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private PieChart pieChart;
    private String selectedState;
    private String selectedDistrict;
    private String selectedCity;
    private ArrayList<String> causes = new ArrayList<>();
    private ArrayList<Integer> counts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause_pie_chart);

        // Get the selected state, district, and city from the intent extras
        selectedState = getIntent().getStringExtra("selectedState");
        selectedDistrict = getIntent().getStringExtra("selectedDistrict");
        selectedCity = getIntent().getStringExtra("selectedCity");

        pieChart = findViewById(R.id.cause_pieChart);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("accident_reports");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String state = snapshot.child("State").getValue(String.class);
                String district = snapshot.child("District").getValue(String.class);
                String city_or_vlg = snapshot.child("City_OR_Village").getValue(String.class);
                String cause = snapshot.child("Selected_Cause").getValue(String.class);
                if (district.equals(selectedDistrict) && city_or_vlg.equals(selectedCity)) {
                    int index = causes.indexOf(cause);
                    if (index == -1) {
                        causes.add(cause);
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




        // Add an OnClickListener to the pie chart
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Retrieve the selected cause
                String selectedCause = causes.get((int) h.getX());

                // Pass the selected state, district, city, and cause to a new activity
                Intent intent = new Intent(CausePieChart.this, CausePrecaution.class);
                intent.putExtra("selectedState", selectedState);
                intent.putExtra("selectedDistrict", selectedDistrict);
                intent.putExtra("selectedCity", selectedCity);
                intent.putExtra("selectedCause", selectedCause);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
                // Do nothing
            }
        });




    }

    private void updateChart() {
        ArrayList cause_piechart_arr = new ArrayList<>();
        for (int i = 0; i < causes.size(); i++) {
            cause_piechart_arr.add(new PieEntry(counts.get(i), causes.get(i)));
        }
        PieDataSet pieDataSet = new PieDataSet(cause_piechart_arr, "Causes of Road Accidents in " + selectedCity);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setText(selectedCity + " Accident Causes Pie Chart");
        pieChart.animate();
        pieChart.invalidate();
    }
}