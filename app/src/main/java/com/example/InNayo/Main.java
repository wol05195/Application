package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        main_button2 = (Button) findViewById(R.id.main_button2);

        main_tv1 = (TextView) findViewById(R.id.main_tv1);
        main_tv2 = (TextView) findViewById(R.id.main_tv2);
        main_tv3 = (TextView) findViewById(R.id.main_tv3);

        Button.OnClickListener onClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.main_button1:
                        intent = new Intent(getApplicationContext(), Congestion.class);
                        startActivity(intent);
                        break;
                    case R.id.main_button2:
                        intent = new Intent(getApplicationContext(), Booking.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        TextView main_tv1 = findViewById(R.id.main_tv1);
        main_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        TextView main_tv2 = findViewById(R.id.main_tv2);
        main_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

        TextView main_tv3 = findViewById(R.id.main_tv3);
        main_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administrator.class);
                startActivity(intent);
            }
        });


        main_button1.setOnClickListener(onClickListener);
        main_button2.setOnClickListener(onClickListener);


    }
}