package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Main extends AppCompatActivity {
    Intent intent;
    Button main_button1, main_button2;
    TextView main_tv1, main_tv2, main_tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_button1 = (Button) findViewById(R.id.main_button1);
        main_button1.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Congestion.class);
        startActivity(intent);
    });

        main_button2 = (Button) findViewById(R.id.main_button2);
        main_button2.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Booking.class);
        startActivity(intent);
    });

        main_tv1 = (TextView)findViewById(R.id.main_tv1);
        main_tv1.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    });
        main_tv2 = (TextView)findViewById(R.id.main_tv2);
        main_tv2.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    });

        main_tv3 = (TextView)findViewById(R.id.main_tv3);
        main_tv3.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Administrator.class);
        startActivity(intent);
    });
    }
}