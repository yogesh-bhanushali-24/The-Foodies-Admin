package com.example.casestudyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView user, food, insertFoods, UserNotice, order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        user = findViewById(R.id.viewUser);
        food = findViewById(R.id.viewFoods);
        insertFoods = findViewById(R.id.viewInsertFoods);
        UserNotice = findViewById(R.id.notice);
        order = findViewById(R.id.viewOrder);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowAllUsersActivity.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowAllFoodsActivity.class);
                startActivity(intent);
            }
        });

        insertFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertFoodActivity.class);
                startActivity(intent);
            }
        });

        UserNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowAllOrdersActivity.class);
                startActivity(intent);
            }
        });

    }
}