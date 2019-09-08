package com.wakilifinder.wakilifinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HomeClient2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client2);
        String practicenumber1  = getIntent().getStringExtra("teamone");
        String practicenumber2  = getIntent().getStringExtra("teamtwo");
        Log.i("OUR VALUE", practicenumber1);
        Log.i("OUR VALUE 2", practicenumber2);
        Toast.makeText(this, ""+practicenumber1, Toast.LENGTH_SHORT).show();
    }
}
