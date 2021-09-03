package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        main_button2 = (Button) findViewById(R.id.main_button2);
        main_button3 = (Button) findViewById(R.id.main_button3);
        main_button4 = (Button) findViewById(R.id.main_button4);
        main_button5 = (Button) findViewById(R.id.main_button5);

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
                    case R.id.main_button3:
                        intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        break;
                    case R.id.main_button4:
                        intent = new Intent(getApplicationContext(), Signup.class);
                        startActivity(intent);
                        break;
                    case R.id.main_button5:
                        intent = new Intent(getApplicationContext(), Administrator.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        main_button1.setOnClickListener(onClickListener);
        main_button2.setOnClickListener(onClickListener);
        main_button3.setOnClickListener(onClickListener);
        main_button4.setOnClickListener(onClickListener);
        main_button5.setOnClickListener(onClickListener);

    }
}