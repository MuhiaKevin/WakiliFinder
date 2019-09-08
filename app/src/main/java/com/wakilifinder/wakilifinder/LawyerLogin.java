package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class LawyerLogin extends AppCompatActivity {

    private Button lawyerSignupbtn, lawyerloginBtn;
    private EditText p105numberfield, passwfield;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_login);

        mAuth = FirebaseAuth.getInstance();


        p105numberfield = findViewById(R.id.p105numLogin);
        passwfield = findViewById(R.id.passwLogin);

        lawyerloginBtn = findViewById(R.id.lawyerSignupbtn1);
        lawyerSignupbtn = findViewById(R.id.lawyerSignupbtn);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(LawyerLogin.this, HomeClient.class));
                }
            }
        };


        // lawyer login when key pressed
        lawyerloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        // open signup activity
        lawyerSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LawyerSignup.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    //

    private void startSignIn() {
        String email = this.p105numberfield.getText().toString();
        String password = this.passwfield.getText().toString();

        // validate email and password
        if (email.isEmpty()) {
            p105numberfield.setError("Required! ");
            p105numberfield.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            passwfield.setError("Required!");
            passwfield.requestFocus();
            return;
        }
        // sing in user when everything is clear
        else {

            final ProgressDialog progressDialog = new ProgressDialog(LawyerLogin.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LawyerLogin.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }



}
