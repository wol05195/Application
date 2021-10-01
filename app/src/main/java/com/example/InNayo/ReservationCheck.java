package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ReservationCheck extends AppCompatActivity {
    EditText reservation_check_place, reservation_check_year, reservation_check_month, reservation_check_date,
            reservation_check_ap, reservation_check_hour, reservation_check_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

        reservation_check_place = (EditText)findViewById(R.id.reservation_check_place);
        reservation_check_year = (EditText)findViewById(R.id.reservation_check_year);
        reservation_check_month = (EditText)findViewById(R.id.reservation_check_month);
        reservation_check_date = (EditText)findViewById(R.id.reservation_check_date);
        reservation_check_ap = (EditText)findViewById(R.id.reservation_check_ap);
        reservation_check_hour = (EditText)findViewById(R.id.reservation_check_hour);
        reservation_check_people = (EditText)findViewById(R.id.reservation_check_people);

        String checkplace = getIntent().getExtras().getString("selectedplace");
        reservation_check_place.setText(checkplace);

        String selectedyear = getIntent().getExtras().getString("selectedyear");
        reservation_check_year.setText(selectedyear);

        String selectedmonth = getIntent().getExtras().getString("selectedmonth");
        reservation_check_month.setText(selectedmonth);

        String selecteddate = getIntent().getExtras().getString("selecteddate");
        reservation_check_date.setText(selecteddate);

        String selectedap = getIntent().getExtras().getString("selectedap");
        if (selectedap.equals("AM") == true){
            reservation_check_ap.setText("오전 ");
        }else{
            reservation_check_ap.setText("오후 ");
        }

        String selectedhour = getIntent().getExtras().getString("selectedhour");
        reservation_check_hour.setText(selectedhour);

        String selectedpp = getIntent().getExtras().getString("selectedpp");
        reservation_check_people.setText(selectedpp);

    }
}