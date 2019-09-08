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

public class ClientLogin extends AppCompatActivity {

    private Button clientSignupbtn, lawyerLoginBtn;
    private EditText emailfield, passwfield;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        mAuth = FirebaseAuth.getInstance();


        emailfield = findViewById(R.id.p105numLogin);
        passwfield = findViewById(R.id.passwLogin);

        clientSignupbtn = findViewById(R.id.clientSignupbtn);
        lawyerLoginBtn = findViewById(R.id.lawyerSignupbtn1);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(ClientLogin.this, HomeClient.class));
                }
            }
        };


        // lawyer login when key pressed
        lawyerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        // open signup activity
        clientSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ClientSignup.class);
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
        String email = this.emailfield.getText().toString();
        String password = this.passwfield.getText().toString();

        // validate email and password
        if (email.isEmpty()) {
            emailfield.setError("Required! ");
            emailfield.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            passwfield.setError("Required!");
            passwfield.requestFocus();
            return;
        }
        // sing in user when everything is clear
        else {
            final ProgressDialog progressDialog = new ProgressDialog(ClientLogin.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(ClientLogin.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}

