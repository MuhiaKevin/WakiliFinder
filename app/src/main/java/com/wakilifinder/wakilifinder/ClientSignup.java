package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientSignup extends AppCompatActivity {

    private EditText emailfield, passwfield, phoneno,confirmpass;
    private Button createAcc;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup);

        mAuth = FirebaseAuth.getInstance();

        phoneno = findViewById(R.id.phonefield);
        emailfield = findViewById(R.id.emailfield);
        confirmpass = findViewById(R.id.confirmfield);
        passwfield = findViewById(R.id.passwfield);
        createAcc = findViewById(R.id.clientlogin);


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailfield.getText().toString();
                // password must have a length of 6 alphanumeric characters
                final String password = passwfield.getText().toString();
                final String phone = phoneno.getText().toString();
                final String confirmpassw = confirmpass.getText().toString();

                final Userclient user = new Userclient(email,password,phone);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ClientSignup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(ClientSignup.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients").child(user_id);
                            current_user_db.setValue(user);

                        }
                    }
                });

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
