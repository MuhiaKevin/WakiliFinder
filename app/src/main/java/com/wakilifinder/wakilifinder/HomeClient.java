package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeClient extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DatasetFire> arrayList;
    private FirebaseRecyclerOptions<DatasetFire> options;
    private FirebaseRecyclerAdapter<DatasetFire, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList =  new ArrayList<DatasetFire>();
        databaseReference =  FirebaseDatabase.getInstance().getReference().child("Users").child("Lawyers");
        databaseReference.keepSynced(true);
        options  = new FirebaseRecyclerOptions.Builder<DatasetFire>().setQuery(databaseReference, DatasetFire.class).build();

        adapter = new FirebaseRecyclerAdapter<DatasetFire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull final DatasetFire datasetFire) {
                firebaseViewHolder.email.setText(datasetFire.getEmail());
                firebaseViewHolder.p105number.setText(datasetFire.getP105number());
                firebaseViewHolder.password.setText(datasetFire.getPassword());
                firebaseViewHolder.practicenumber.setText(datasetFire.getPracticenumber());

                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeClient.this, HomeClient2.class);
                        intent.putExtra("teamone",datasetFire.getP105number() );
                        intent.putExtra("teamtwo", datasetFire.getPracticenumber());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(HomeClient.this).inflate(R.layout.row,parent,false));
            }
        };



        recyclerView.setAdapter(adapter);
    }
}
