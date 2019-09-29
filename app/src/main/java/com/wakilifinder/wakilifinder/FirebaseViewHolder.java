package com.wakilifinder.wakilifinder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView email,p105number,password,practicenumber;
    public ImageView imageurl;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        email = itemView.findViewById(R.id.email);
        imageurl = itemView.findViewById(R.id.profileImage);
        p105number = itemView.findViewById(R.id.p105number);
        password = itemView.findViewById(R.id.password);
        practicenumber = itemView.findViewById(R.id.practiceNumber);

    }
}
