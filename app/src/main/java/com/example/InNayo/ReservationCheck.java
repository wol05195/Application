package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReservationCheck extends AppCompatActivity {
    TextView reservation_check_place, reservation_check_year, reservation_check_month, reservation_check_date, reservation_check_time, reservation_check_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

        reservation_check_place = (TextView)findViewById(R.id.reservation_check_place);
        reservation_check_year = (TextView)findViewById(R.id.reservation_check_year);

        String checkplace = getIntent().getExtras().getString("selectedplace");
        reservation_check_place.setText(checkplace);

//        String selectedyear = getIntent().getExtras().getString("selectedyear");
//        reservation_check_year.setText(selectedyear);
//
//        String selectedmonth = getIntent().getExtras().getString("selectedmonth");
//        reservation_check_month.setText(selectedmonth);
//
//        String selecteddate = getIntent().getExtras().getString("selecteddate");
//        reservation_check_date.setText(selecteddate);
//
//        String selectedtime = getIntent().getExtras().getString("selectedtime");
//        reservation_check_time.setText(selectedtime);

//        String selectedap = getIntent().getExtras().getString("selectedap");
//        reservation_ap.setText(selectedap + " ");

//        String selectedpeople = getIntent().getExtras().getString("selectedpeople");
//        reservation_people.setText(selectedpeople);
    }
}