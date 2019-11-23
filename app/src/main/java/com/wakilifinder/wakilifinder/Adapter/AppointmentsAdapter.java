package com.wakilifinder.wakilifinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wakilifinder.wakilifinder.EditTaskDesk;
import com.wakilifinder.wakilifinder.Model.Appointments;
import com.wakilifinder.wakilifinder.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder>{

    Context context;
    ArrayList<Appointments> myAppointments;

    public AppointmentsAdapter(Context c, ArrayList<Appointments> p) {
        context = c;
        myAppointments = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsAdapter.MyViewHolder holder, int position) {
        holder.titledoes.setText(myAppointments.get(position).getTitledoes());
        holder.descdoes.setText(myAppointments.get(position).getDescdoes());
        holder.datedoes.setText(myAppointments.get(position).getDatedoes());

        final String getTitleDoes = myAppointments.get(position).getTitledoes();
        final String getDescDoes = myAppointments.get(position).getDescdoes();
        final String getDateDoes = myAppointments.get(position).getDatedoes();
        final String getKeyDoes = myAppointments.get(position).getKeydoes();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, EditTaskDesk.class);
                aa.putExtra("titledoes", getTitleDoes);
                aa.putExtra("descdoes", getDescDoes);
                aa.putExtra("datedoes", getDateDoes);
                aa.putExtra("keydoes", getKeyDoes);
                context.startActivity(aa);
            }
        });
    }



    @Override
    public int getItemCount() {
        return myAppointments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes, descdoes, datedoes, keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            descdoes = (TextView) itemView.findViewById(R.id.descdoes);
            datedoes = (TextView) itemView.findViewById(R.id.datedoes);
        }
    }

}
