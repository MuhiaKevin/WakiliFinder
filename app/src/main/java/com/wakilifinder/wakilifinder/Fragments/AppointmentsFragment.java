package com.wakilifinder.wakilifinder.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Adapter.AppointmentsAdapter;
import com.wakilifinder.wakilifinder.Model.Appointments;
import com.wakilifinder.wakilifinder.NewTaskAct;
import com.wakilifinder.wakilifinder.R;

import java.util.ArrayList;

public class AppointmentsFragment extends Fragment {
    TextView titlepage, subtitle, endpage;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView appointments;
    ArrayList<Appointments> list;
    AppointmentsAdapter appointmentsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_appointments, container, false);

        titlepage = view.findViewById(R.id.titlepage);
        subtitle = view.findViewById(R.id.subtitlepage);
        endpage = view.findViewById(R.id.endpage);

        btnAddNew = view.findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), NewTaskAct.class);
                startActivity(a);
            }
        });

        //working with data
        appointments = view.findViewById(R.id.ourdoes);
        appointments.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<Appointments>();

        readAppointments();





        return view;
    }

    private void readAppointments() {
        // get data from firebase

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("Lawyers").child(firebaseUser.getUid()).child("appointments");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //code
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    Appointments p = dataSnapshot1.getValue(Appointments.class);
                    list.add(p);
                }
                appointmentsAdapter = new AppointmentsAdapter(getContext(),list);
                appointments.setAdapter(appointmentsAdapter);
                appointmentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //code
            }
        });
    }


}
