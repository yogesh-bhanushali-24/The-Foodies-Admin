package com.example.casestudyadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShowAllUsersActivity extends AppCompatActivity {

    RecyclerView ShowUsersData;
    ShowAllUsersadapter usersadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        //getSupportActionBar().hide();


        ShowUsersData = findViewById(R.id.viewUsersRecyclerview);
        ShowUsersData.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Usermodel> options =
                new FirebaseRecyclerOptions.Builder<Usermodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserDetail"), Usermodel.class)
                        .build();

        usersadapter = new ShowAllUsersadapter(options);
        ShowUsersData.setAdapter(usersadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        usersadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersadapter.stopListening();
    }

    //search code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchuser, menu);
        MenuItem item = menu.findItem(R.id.searchU);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchUserByNumber(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchUserByNumber(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchUserByNumber(String s) {

        FirebaseRecyclerOptions<Usermodel> options =
                new FirebaseRecyclerOptions.Builder<Usermodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserDetail").orderByChild("mobile").startAt(s).endAt(s + "\uf8ff"), Usermodel.class)
                        .build();

        usersadapter = new ShowAllUsersadapter(options);
        usersadapter.startListening();
        ShowUsersData.setAdapter(usersadapter);

    }
    //close search code


}