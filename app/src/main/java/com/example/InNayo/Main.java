package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Main extends AppCompatActivity {
    Intent intent;
    Button main_button1, main_button2;
    TextView main_tv1, main_tv2, main_tv3, main_tv4, main_tv5, main_view1;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    String logined_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name", "");

        main_button1 = (Button) findViewById(R.id.main_button1);
        main_button2 = (Button) findViewById(R.id.main_button2);

        main_tv1 = (TextView) findViewById(R.id.main_tv1);
        main_tv2 = (TextView) findViewById(R.id.main_tv2);
        main_tv3 = (TextView) findViewById(R.id.main_tv3);
        main_tv4 = (TextView) findViewById(R.id.main_tv4);
        main_tv5 = (TextView) findViewById(R.id.main_tv5);
        main_view1 = (TextView) findViewById(R.id.main_view1);

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
        main_button1.setOnClickListener(onClickListener);
        main_button2.setOnClickListener(onClickListener);

        main_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        main_tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

        main_tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Administrator.class);
                startActivity(intent);
            }
        });

        main_tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminPage.class);
                startActivity(intent);
            }
        });

        main_tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putString("logined_name", "");
                        editor.apply();
                        main_tv1.setVisibility(View.VISIBLE);
                        main_tv2.setVisibility(View.VISIBLE);
                        main_tv5.setVisibility(View.GONE);
                        main_view1.setVisibility(View.VISIBLE);
                        refresh();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.create().show();
            }
        });

        if(logined_name != ""){
            main_tv1.setVisibility(View.GONE);
            main_tv2.setVisibility(View.GONE);
            main_tv5.setVisibility(View.VISIBLE);
            main_view1.setVisibility(View.GONE);
        }
        else if(logined_name == ""){
            main_tv1.setVisibility(View.VISIBLE);
            main_tv2.setVisibility(View.VISIBLE);
            main_tv5.setVisibility(View.GONE);
            main_view1.setVisibility(View.VISIBLE);
        }
    }
    public void refresh(){
        startActivity(new Intent(this, Main.class));
        overridePendingTransition(0, 0);
    }
}