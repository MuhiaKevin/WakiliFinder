package com.wakilifinder.wakilifinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class LawyerSignup1 extends AppCompatActivity {


    private EditText emailfield, passwfield, p105numberfield,praticenumberfield,confirmpassfield;
    private Button createAccbtn;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private static final String TAG = "MUNYAMUNYA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_signup1);
        Log.i(TAG,"STARTED APP");

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

                final ProgressDialog progressDialog = new ProgressDialog(LawyerSignup1.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Registering...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show();

                Log.i(TAG,"CREATING USER");

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(LawyerSignup1.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(LawyerSignup1.this, "Sign up error", Toast.LENGTH_SHORT).show();
                        }

                        else{
//                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Lawyers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            current_user_db.setValue(user);
                            progressDialog.dismiss();
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
        FirebaseAuth.getInstance().removeAuthStateListener(firebaseAuthListener);
    }
}
