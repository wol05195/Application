package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Main extends AppCompatActivity {
    Intent intent;
    Button b_congestion, b_reservation;
    TextView tv_login, tv_signup, tv_administrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    b_congestion = (Button) findViewById(R.id.b_congestion);
    b_congestion.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Congestion.class);
        startActivity(intent);
    });

    b_reservation = (Button) findViewById(R.id.b_reservation);
    b_reservation.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Booking.class);
        startActivity(intent);
    });

    tv_login = (TextView)findViewById(R.id.tv_login);
    tv_login.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    });
    tv_signup = (TextView)findViewById(R.id.tv_signup);
    tv_signup.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    });

    tv_administrator = (TextView)findViewById(R.id.tv_administrator);
    tv_administrator.setOnClickListener(v -> {
        intent = new Intent(getApplicationContext(), Administrator.class);
        startActivity(intent);
    });
    }
}