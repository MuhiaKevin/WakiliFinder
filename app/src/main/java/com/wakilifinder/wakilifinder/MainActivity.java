package com.wakilifinder.wakilifinder;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button clientacc,lawyeracc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientacc = (Button) findViewById(R.id.clientAcc);
        lawyeracc = findViewById(R.id.lawyerAcc);

        clientacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ClientLogin.class);
                startActivity(i);
            }
        });

        lawyeracc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LawyerLogin.class);
                startActivity(intent);
            }
        });

    }

}
