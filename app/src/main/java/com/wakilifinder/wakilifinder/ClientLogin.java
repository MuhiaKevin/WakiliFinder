package com.wakilifinder.wakilifinder;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientLogin extends AppCompatActivity {

    private Button clientsignupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        clientsignupbtn =  findViewById(R.id.clientSignupbtn);

        clientsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ClientSignup.class);
                startActivity(i);
            }
        });

    }
}
