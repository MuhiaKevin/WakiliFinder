package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class LawyerSignup extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private EditText emailfield, passwfield, p105numberfield,praticenumberfield,confirmpassfield;
    private Button createAccbtn;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private static final String TAG = "MUNYAMUNYA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_signup2_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        emailfield = findViewById(R.id.lawyeremailfield);
        p105numberfield = findViewById(R.id.p105number);
        praticenumberfield = findViewById(R.id.practiceNumber);
        passwfield = findViewById(R.id.passwfield);
        confirmpassfield = findViewById(R.id.confirmfield);
        createAccbtn = findViewById(R.id.lawyerSignupbtn1);

        createAccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailfield.getText().toString();
                final String p105strng = p105numberfield.getText().toString();
                final String practicenumstrng =  praticenumberfield.getText().toString();

                // password must have a length of 6 alphanumeric characters
                final String password = passwfield.getText().toString();
                final String confirmpassw = confirmpassfield.getText().toString();

                // validating inputs

                if (email.isEmpty()) {
                    emailfield.setError("Required! ");
                    emailfield.requestFocus();
                    return;
                }
                if (p105strng.isEmpty()) {
                    p105numberfield.setError("Required!");
                    p105numberfield.requestFocus();
                    return;
                }

                if (practicenumstrng.isEmpty()) {
                    praticenumberfield.setError("Required!");
                    praticenumberfield.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwfield.setError("Required!");
                    passwfield.requestFocus();
                    return;
                }

                if (confirmpassw.isEmpty()) {
                    confirmpassfield.setError("Required!");
                    confirmpassfield.requestFocus();
                    return;
                }


                if(!TextUtils.equals(password,confirmpassw)){
                    confirmpassfield.setError("Passwords don't match!");
                    confirmpassfield.requestFocus();
                    return;
                }

                // TODO
                // validating legitimacy of lawyer is done around here

                Log.i(TAG,"STARTING PROGRESS DIALOG");

                final UserLawyer user = new UserLawyer(email,p105strng,practicenumstrng,password);

                final ProgressDialog progressDialog = new ProgressDialog(LawyerSignup.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Registering...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show();

                Log.i(TAG,"CREATING USER");

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(LawyerSignup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(LawyerSignup.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }

                        else{
//                          String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Lawyers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            current_user_db.setValue(user);
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        });




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(firebaseAuthListener);
    }
}
