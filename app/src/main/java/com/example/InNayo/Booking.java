package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Booking extends AppCompatActivity {
    Button booking_button1;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        booking_button1 = (Button) findViewById(R.id.booking_button1);
        booking_button1.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Reservation.class);
            startActivity(intent);
        });
    }
}