package com.wakilifinder.wakilifinder;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ClientLogin extends AppCompatActivity {

    private Button signupbtn, loginbtn;
    private EditText emailfield, passwfield;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        mAuth = FirebaseAuth.getInstance();


        emailfield = findViewById(R.id.emailLogin);
        passwfield = findViewById(R.id.passwLogin);

        signupbtn = findViewById(R.id.clientSignupbtn);
        loginbtn = findViewById(R.id.clientlogin);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(ClientLogin.this, HomePage.class));
                }
            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
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

    private void startSignIn() {

        String email = this.emailfield.getText().toString();
        String password = this.passwfield.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(ClientLogin.this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(ClientLogin.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}

