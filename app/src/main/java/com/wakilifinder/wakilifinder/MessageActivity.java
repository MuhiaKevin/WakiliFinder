package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Model.Userclient;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        String user = intent.getStringExtra("user");

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (user.equals("client")){
            reference = FirebaseDatabase.getInstance().getReference("Users").child("Lawyers").child(userid);
        }
        else if (user.equals("lawyer")){
            reference = FirebaseDatabase.getInstance().getReference("Users").child("Clients").child(userid);
        }


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Userclient user =  dataSnapshot.getValue(Userclient.class);
                username.setText(user.getEmail());
                profile_image.setImageResource(R.mipmap.ic_launcher);            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
