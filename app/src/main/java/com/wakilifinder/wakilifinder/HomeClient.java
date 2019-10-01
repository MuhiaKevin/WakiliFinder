package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeClient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
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
        toolbar = findViewById(R.id.toolbar);
        // set title color to white
        toolbar.setTitleTextColor(getResources().getColor(R.color.whitte));

        // set three dot color as white, new ico is needed
        toolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_black_24dp));

        setSupportActionBar(toolbar);


        // drawer menu settings
        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        // set hamburger color white
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whitte));
        drawerToggle.setDrawerIndicatorEnabled(true); // enable hambugrer
        drawerToggle.syncState();


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

//                Picasso.get().load(datasetFire.getImageurl()).fit().centerCrop().into(firebaseViewHolder.imageurl);
                Glide
                        .with(getApplicationContext())
                        .load(datasetFire.getImageurl())
                        .apply(new RequestOptions().override(1000, 400))
                        .into(firebaseViewHolder.imageurl);

                // show in recyclerview
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

        // select navigation first

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set menu inflater
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "Settings button clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contact:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeClient.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                Toast.makeText(this, "Home button clicked",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
