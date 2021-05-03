package com.example.casestudyadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShowAllFoodsActivity extends AppCompatActivity {

    RecyclerView ShowFoodRecycler;
    ShowAllFoodsadapter showAllFoodsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_foods);
        //action bar hide
        getSupportActionBar().hide();


        ShowFoodRecycler = findViewById(R.id.ShowAllFoodsRecyclerView);

        ShowFoodRecycler.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<foodmodel> options =
                new FirebaseRecyclerOptions.Builder<foodmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), foodmodel.class)
                        .build();

        showAllFoodsadapter = new ShowAllFoodsadapter(options);
        ShowFoodRecycler.setAdapter(showAllFoodsadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllFoodsadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showAllFoodsadapter.stopListening();
    }
}