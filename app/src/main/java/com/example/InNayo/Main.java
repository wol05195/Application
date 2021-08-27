package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Main extends AppCompatActivity {
    Intent intent;
    Button main_button1, main_button2, main_button3, main_button4, main_button5;

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

        main_button3 = (Button) findViewById(R.id.main_button3);
        main_button3.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        main_button4 = (Button) findViewById(R.id.main_button4);
        main_button4.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
        });

        main_button5 = (Button) findViewById(R.id.main_button5);
        main_button5.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Administrator.class);
            startActivity(intent);
        });


    }
}