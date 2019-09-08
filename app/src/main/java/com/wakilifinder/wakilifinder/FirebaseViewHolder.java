package com.wakilifinder.wakilifinder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView email,p105number,password,practicenumber;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        email = itemView.findViewById(R.id.email);
        p105number = itemView.findViewById(R.id.p105number);
        password = itemView.findViewById(R.id.password);
        practicenumber = itemView.findViewById(R.id.practiceNumber);

    }
}
