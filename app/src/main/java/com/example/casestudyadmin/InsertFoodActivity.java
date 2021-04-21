package com.example.casestudyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InsertFoodActivity extends AppCompatActivity {

    private String sp;
    public Spinner spinner;
    public ImageView foodImage;
    public EditText foodName, foodDescription, foodPrice;
    public Button insertBtn, imageChooseBtn, resetBtn;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_food);
        getSupportActionBar().hide();

        foodName = findViewById(R.id.insertFoodName);
        foodDescription = findViewById(R.id.insertFoodDescription);
        foodPrice = findViewById(R.id.insertFoodPrice);
        foodImage = findViewById(R.id.insertFoodIm);
        insertBtn = findViewById(R.id.insertFoodButton);
        imageChooseBtn = findViewById(R.id.chooseImage);
        resetBtn = findViewById(R.id.resetFoodButton);
        spinner = findViewById(R.id.categoriesSpinner);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Food");


        //spinner code
        List<String> categories = new ArrayList<>();
        categories.add(0, "Choose category");
        categories.add("breakfast");
        categories.add("punjabi");
        categories.add("gujarati");
        categories.add("southindian");
        categories.add("italian");
        categories.add("chinese");
        categories.add("colddrink");
        categories.add("hotdrink");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("choose categories")) {
                    //do nothing
                } else {
                    sp = adapterView.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //end spinner code


        //image insert code
        imageChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });


        //on click of insert button
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (foodName.getText().toString().isEmpty() || foodPrice.getText().toString().isEmpty() || foodDescription.getText().toString().isEmpty() || sp.isEmpty() || foodImage == null) {
                    Toast.makeText(InsertFoodActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImage();
                }

            }
        });


        //reset data
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodName.getText().clear();
                foodDescription.getText().clear();
                foodPrice.getText().clear();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                foodImage.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImage() {
        if (FilePathUri != null) {
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            @SuppressWarnings("VisibleForTests")
                            String sName = foodName.getText().toString();
                            String sDescription = foodDescription.getText().toString();
                            String sPrice = foodPrice.getText().toString();
                            String sImage = uri.toString();

                            HashMap<String, String> foodMap = new HashMap<>();
                            foodMap.put("name", sName);
                            foodMap.put("description", sDescription);
                            foodMap.put("image", sImage);
                            foodMap.put("price", sPrice);
                            foodMap.put("categories", sp);
                            databaseReference.push().setValue(foodMap);

                        }
                    });


                }
            });


        } else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }

    }


}