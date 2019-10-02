package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakilifinder.wakilifinder.Model.UserLawyer;

public class HomeLawyer extends AppCompatActivity {

    private CircleImageView profile_image;
    private TextView username;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lawyer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Lawyers").child(firebaseUser.getUid());

        // set user information on the activity
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserLawyer user = dataSnapshot.getValue(UserLawyer.class);

                username.setText(user.getEmail());
                //Glide.with(getApplicationContext()).load(user.getProfileimage()).into(profile_image);
                profile_image.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // set menu layout on the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // if log out button on the appbar then the app logs the user out
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // log out button
            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeLawyer.this, LawyerLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }

        return false;
    }

}
