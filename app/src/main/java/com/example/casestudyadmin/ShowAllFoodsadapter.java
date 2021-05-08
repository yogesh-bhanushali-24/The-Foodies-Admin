package com.example.casestudyadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowAllFoodsadapter extends FirebaseRecyclerAdapter<foodmodel, ShowAllFoodsadapter.foodviewholder> {
    public ShowAllFoodsadapter(@NonNull FirebaseRecyclerOptions<foodmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final foodviewholder holder, final int position, @NonNull final foodmodel model) {
        holder.FName.setText(model.getName());
        holder.FCategory.setText(model.getCategories());
        holder.FPrice.setText(model.getPrice() + "₹");
        holder.FDescription.setText(model.getDescription());
        Glide.with(holder.FImage.getContext()).load(model.getImage()).into(holder.FImage);

        //edit Food dialog plus
        holder.FEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.FName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.fooddialogcontent))
                        .setExpanded(true, 1500)
                        .create();

                View foodView = dialogPlus.getHolderView();
                EditText updateFoodName = foodView.findViewById(R.id.foodDialogName);
                EditText updateFoodPrice = foodView.findViewById(R.id.foodDialogPrice);
                EditText updateFoodDescription = foodView.findViewById(R.id.foodDialogDescription);
                EditText updateFoodCategory = foodView.findViewById(R.id.foodDialogCategory);
                ImageView updateFoodImage = foodView.findViewById(R.id.foodDialogImage);
                Button updateFoodBtn = foodView.findViewById(R.id.foodDialogUpdateBtn);

                updateFoodName.setText(model.getName());
                updateFoodPrice.setText(model.getPrice());
                updateFoodCategory.setText(model.getCategories());
                updateFoodDescription.setText(model.getDescription());
                Glide.with(updateFoodImage.getContext()).load(model.getImage()).into(updateFoodImage);

                dialogPlus.show();

                //onClick of update button
                updateFoodBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("name", updateFoodName.getText().toString());
                        map.put("description", updateFoodDescription.getText().toString());
                        map.put("price", updateFoodPrice.getText().toString());
                        map.put("categories", updateFoodCategory.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Food")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

    }

    @NonNull
    @Override
    public foodviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodscardview, parent, false);
        return new foodviewholder(view);
    }

    class foodviewholder extends RecyclerView.ViewHolder {
        ImageView FImage;
        TextView FName, FPrice, FDescription, FCategory;
        MaterialButton FEdit, FDelete;

        public foodviewholder(@NonNull View itemView) {
            super(itemView);
            FImage = itemView.findViewById(R.id.foodimage);
            FName = itemView.findViewById(R.id.foodname);
            FPrice = itemView.findViewById(R.id.foodprice);
            FDescription = itemView.findViewById(R.id.fooddescription);
            FCategory = itemView.findViewById(R.id.foodcategory);
            FEdit = itemView.findViewById(R.id.foodEditBtn);
            FDelete = itemView.findViewById(R.id.foodDeleteBtn);

        }
    }

}
