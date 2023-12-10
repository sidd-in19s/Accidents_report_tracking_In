package com.example.accident_statistics_calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ClickBarActivity extends AppCompatActivity {
    private TextView tvSelectedState;
    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    public String selectedState;
    DatabaseReference reference;
    MainModel mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_bar);

        selectedState = getIntent().getStringExtra("selectedState");
        recyclerView =(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("accident_reports"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
        /**Query query = reference.orderByChild("State").equalTo(selectedState);

        String State_from_mainModel = mm.getState().;
        System.out.println(State_from_mainModel).Colo;
        if (selectedState.equals(State_from_mainModel)){
            recyclerView.setAdapter(mainAdapter);
        }**/
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}