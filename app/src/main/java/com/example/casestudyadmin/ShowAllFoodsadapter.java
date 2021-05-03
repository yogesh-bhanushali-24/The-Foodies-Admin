package com.example.casestudyadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowAllFoodsadapter extends FirebaseRecyclerAdapter<foodmodel, ShowAllFoodsadapter.foodviewholder> {
    public ShowAllFoodsadapter(@NonNull FirebaseRecyclerOptions<foodmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodviewholder holder, int position, @NonNull foodmodel model) {
        holder.FName.setText(model.getName());
        holder.FCategory.setText(model.getCategories());
        holder.FPrice.setText(model.getPrice() + "₹");
        holder.FDescription.setText(model.getDescription());
        Glide.with(holder.FImage.getContext()).load(model.getImage()).into(holder.FImage);

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

        public foodviewholder(@NonNull View itemView) {
            super(itemView);
            FImage = itemView.findViewById(R.id.foodimage);
            FName = itemView.findViewById(R.id.foodname);
            FPrice = itemView.findViewById(R.id.foodprice);
            FDescription = itemView.findViewById(R.id.fooddescription);
            FCategory = itemView.findViewById(R.id.foodcategory);

        }
    }

}
