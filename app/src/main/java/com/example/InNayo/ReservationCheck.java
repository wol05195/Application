package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReservationCheck extends AppCompatActivity {
    TextView reservation_check_place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

        reservation_check_place = (TextView)findViewById(R.id.reservation_check_place);

        String checkplace = getIntent().getExtras().getString("selectedplace");
        reservation_check_place.setText(checkplace);
    }
}