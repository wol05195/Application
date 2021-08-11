package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Booking extends AppCompatActivity {
    Button b_ok;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        b_ok = (Button) findViewById(R.id.b_ok);
        b_ok.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Reservation.class);
            startActivity(intent);
        });
    }
}