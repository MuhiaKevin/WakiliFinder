package com.wakilifinder.wakilifinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LawyerSignup1 extends AppCompatActivity {


    private EditText emailfield, passwfield, p105numberfield,praticenumberfield,confirmpassfield;
    private Button createAccbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_signup1);

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



                    }
                });




    }
}
