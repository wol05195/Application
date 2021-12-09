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
    Button main_button1, main_button2, main_button3, main_button4, main_button5, main_button6, main_button7;
    TextView main_tv1, main_tv2, main_tv3, main_tv4, main_tv5, main_tv6, main_view1, main_view2;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    String logined_name, logined_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name", "");
        logined_id = pref.getString("logined_id", "");

        main_button1 = (Button) findViewById(R.id.main_button1);
        main_button2 = (Button) findViewById(R.id.main_button2);
        main_button3 = (Button) findViewById(R.id.main_button3);
        main_button5 = (Button) findViewById(R.id.main_button5);
        main_button6 = (Button) findViewById(R.id.main_button6);
        main_button7 = (Button) findViewById(R.id.main_button7);

        main_tv1 = (TextView) findViewById(R.id.main_tv1);
        main_tv2 = (TextView) findViewById(R.id.main_tv2);
        main_tv3 = (TextView) findViewById(R.id.main_tv3);
        main_tv5 = (TextView) findViewById(R.id.main_tv5);
        main_tv6 = (TextView) findViewById(R.id.main_tv6);
        main_view1 = (TextView) findViewById(R.id.main_view1);
        main_view2 = (TextView) findViewById(R.id.main_view2);

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
                        Intent intent = new Intent(getApplicationContext(), Administrator.class);
                        startActivity(intent);
                        break;
//                    case R.id.main_button4:
//                        intent = new Intent(getApplicationContext(), adminPage.class);
//                        startActivity(intent);
//                        break;
                    case R.id.main_button5:
                        intent = new Intent(getApplicationContext(), adminReservation.class);
                        startActivity(intent);
                        break;
                    case R.id.main_button6:
                        intent = new Intent(getApplicationContext(), adminMember.class);
                        startActivity(intent);
                        break;
                    case R.id.main_button7:
                        intent = new Intent(getApplicationContext(), adminFacilities.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        main_button1.setOnClickListener(onClickListener);
        main_button2.setOnClickListener(onClickListener);
        main_button3.setOnClickListener(onClickListener);
        main_button5.setOnClickListener(onClickListener);
        main_button6.setOnClickListener(onClickListener);
        main_button7.setOnClickListener(onClickListener);

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
                Intent intent = new Intent(getApplicationContext(), MyInformation.class);
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
                        editor.putString("logined_id", "");
                        editor.apply();
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

        main_tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBookingCheck.class);
                startActivity(intent);
            }
        });
        String developer = logined_name.replace(" ", "");
        if(developer.equals("개발자")==true){
            main_tv1.setVisibility(View.GONE);
            main_tv2.setVisibility(View.GONE);
            main_tv3.setVisibility(View.GONE);
            main_view1.setVisibility(View.GONE);
            main_view2.setVisibility(View.GONE);
            main_tv5.setVisibility(View.VISIBLE);
            main_button1.setVisibility(View.GONE);
            main_button2.setVisibility(View.GONE);
            main_button3.setVisibility(View.GONE);
            main_button5.setVisibility(View.VISIBLE);
            main_button6.setVisibility(View.VISIBLE);
            main_button7.setVisibility(View.VISIBLE);
        }
        else if(logined_name == ""){
            main_tv1.setVisibility(View.VISIBLE);
            main_tv2.setVisibility(View.VISIBLE);
            main_tv3.setVisibility(View.GONE);
            main_tv5.setVisibility(View.GONE);
            main_tv6.setVisibility(View.GONE);
            main_view1.setVisibility(View.GONE);

        }else if(logined_name != ""){
            if(logined_id ==""){
                main_tv3.setVisibility(View.GONE);
                main_view1.setVisibility(View.GONE);
            }else{
                main_tv3.setVisibility(View.VISIBLE);
                main_view1.setVisibility(View.VISIBLE);
            }
            main_tv1.setVisibility(View.GONE);
            main_tv2.setVisibility(View.GONE);
            main_tv5.setVisibility(View.VISIBLE);
            main_tv6.setVisibility(View.VISIBLE);
        }
    }
    public void refresh(){
        startActivity(new Intent(this, Main.class));
        overridePendingTransition(0, 0);
    }
}