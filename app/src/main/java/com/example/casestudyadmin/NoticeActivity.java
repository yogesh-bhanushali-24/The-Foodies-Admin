package com.example.casestudyadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoticeActivity extends AppCompatActivity {
    private EditText note;
    private MaterialButton pub, upub;

    FirebaseDatabase database;
    DatabaseReference reference;
    TextView txtMarquee;
    String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getSupportActionBar().hide();

        note = findViewById(R.id.EditNotice);
        pub = findViewById(R.id.publish);
        upub = findViewById(R.id.unpublish);
        txtMarquee = findViewById(R.id.MarqueeText);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Notice");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    txtMarquee.setText("Notice not published on client side ");
                    txtMarquee.setSelected(true);
                } else {
                    s1 = snapshot.getValue().toString();
                    txtMarquee.setText(s1);
                    txtMarquee.setSelected(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!note.getText().toString().isEmpty()) {
                    String noteValue = note.getText().toString();
                    reference.setValue(noteValue);
                    note.setText("");
                    Toast.makeText(NoticeActivity.this, "Notice Published Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NoticeActivity.this, "Field Is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.removeValue();
                Toast.makeText(NoticeActivity.this, "Notice Unpublished successfully", Toast.LENGTH_SHORT).show();

            }
        });


    }
}