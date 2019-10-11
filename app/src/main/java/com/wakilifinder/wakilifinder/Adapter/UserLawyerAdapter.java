package com.wakilifinder.wakilifinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wakilifinder.wakilifinder.MessageActivity;
import com.wakilifinder.wakilifinder.Model.UserLawyer;
import com.wakilifinder.wakilifinder.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserLawyerAdapter extends RecyclerView.Adapter<UserLawyerAdapter.ViewHolder> {
    private Context mContext;
    private List<UserLawyer> mUsers;

    public UserLawyerAdapter(Context mContext, List<UserLawyer> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row, parent, false);
        return new UserLawyerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final UserLawyer user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.p105number.setText(user.p105number);
        holder.practicenumber.setText(user.practicenumber);

        if(user.getImageurl() == null){

            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Glide.with(mContext).load(user.getImageurl()).apply(new RequestOptions().override(1000, 400)).into(holder.profile_image);


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MessageActivity.class);
                intent.putExtra("userid", user.getUserid());
                intent.putExtra("user","client");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username,p105number,practicenumber;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            p105number = itemView.findViewById(R.id.p105number);
            practicenumber = itemView.findViewById(R.id.practiceNumber);
        }
    }
}
