package com.example.accident_statistics_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdviceActivity extends AppCompatActivity {

    private Button accident_report_redirect, statistics_graph_redirect, logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        accident_report_redirect = findViewById(R.id.accident_report_redirect);
        statistics_graph_redirect = findViewById(R.id.statistics_graph_redirect);
        logout_btn = findViewById(R.id.logout_btn);

        accident_report_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdviceActivity.this, MainActivity222.class);
                startActivity(intent);
            }
        });

        statistics_graph_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdviceActivity.this, BarChartActivity.class);
                startActivity(intent);
            }
        });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                finish();


            }
        });


    }

    @Override
    public void onBackPressed() {
        // Do nothin'
    }


}