package com.example.casestudyadmin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ShowAllUsersadapter extends FirebaseRecyclerAdapter<Usermodel, ShowAllUsersadapter.UsersViewHolder> {
    public ShowAllUsersadapter(@NonNull FirebaseRecyclerOptions<Usermodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position, @NonNull final Usermodel model) {
        holder.sName.setText(model.getName());
        holder.sEmail.setText(model.getEmail());
        holder.sNumber.setText(model.getMobile());

        //update users connect with dialogcontent

        holder.sEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.sName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1100)
                        .create();


                View viewDialog = dialogPlus.getHolderView();
                EditText dName = viewDialog.findViewById(R.id.dialogName);
                EditText dNumber = viewDialog.findViewById(R.id.dialogNumber);
                EditText dEmail = viewDialog.findViewById(R.id.dialogEmail);
                Button dUpdate = viewDialog.findViewById(R.id.dialogUpdateBtn);


                dName.setText(model.getName());
                dNumber.setText(model.getMobile());
                dEmail.setText(model.getEmail());

                dialogPlus.show();

                dUpdate.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", dName.getText().toString());
                        map.put("email", dEmail.getText().toString());
                        map.put("mobile", dNumber.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("UserDetail")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(view.getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                        Toast.makeText(view.getContext(), "Update unsuccessfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

            }
        });


        //end update user part

//        holder.sDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(holder.sName.getContext());
//                builder.setTitle("Delete User");
//            }
//        });


        //Delete User

        holder.sDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.sName.getContext());
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure want to Delete..?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase.getInstance().getReference().child("UserDetail")
//                                .child(getRef(position).getKey()).removeValue();

                        Toast.makeText(view.getContext(), "User Successfully Deleted", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userscardview, parent, false);
        return new UsersViewHolder(view);
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView sName, sEmail, sNumber;
        Button sEdit, sDelete;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            sName = itemView.findViewById(R.id.userNameTxt);
            sEmail = itemView.findViewById(R.id.userEmailTxt);
            sNumber = itemView.findViewById(R.id.userNumberTxt);
            sEdit = itemView.findViewById(R.id.userEditBtn);
            sDelete = itemView.findViewById(R.id.userDeleteBtn);
        }
    }

}
