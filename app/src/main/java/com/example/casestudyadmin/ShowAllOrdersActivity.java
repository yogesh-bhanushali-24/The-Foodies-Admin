package com.example.casestudyadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShowAllOrdersActivity extends AppCompatActivity {
    RecyclerView ShowOrderRecycler;
    ShowAllOrdersadapter Ordersadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_orders);
        ShowOrderRecycler = findViewById(R.id.ShowAllOrdersRecyclerView);
        ShowOrderRecycler.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().hide();
        //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseRecyclerOptions<Ordermodel> options =
                new FirebaseRecyclerOptions.Builder<Ordermodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order"), Ordermodel.class)
                        .build();

        Ordersadapter = new ShowAllOrdersadapter(options);
        ShowOrderRecycler.setAdapter(Ordersadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Ordersadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Ordersadapter.stopListening();
    }

}