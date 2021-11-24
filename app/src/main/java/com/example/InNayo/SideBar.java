package com.example.InNayo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SideBar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
    }
}

class SideBarView extends RelativeLayout implements View.OnClickListener {
    Intent intent;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText et_side_name;
    /** 메뉴버튼 클릭 이벤트 리스너 */
    public EventListener listener;
    public void setEventListener(EventListener l) {
        listener = l; }
    /** 메뉴버튼 클릭 이벤트 리스너 인터페이스 */
    public interface EventListener {
        // 닫기 버튼 클릭 이벤트
        void btnLevel1();

    }
    public SideBarView(Context context) {
        this(context, null);
        init();
    }
    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.activity_side_bar, this, true);

        findViewById(R.id.cross_btn1).setOnClickListener(this);
        findViewById(R.id.side_menu1).setOnClickListener(new View.OnClickListener() { //홈
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main.class);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.side_menu2).setOnClickListener(new View.OnClickListener() { //혼잡도
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Congestion.class);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.side_menu3).setOnClickListener(new View.OnClickListener() { //예약하기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Booking.class);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.side_menu4).setOnClickListener(new View.OnClickListener() { //개인정보수정
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyInformation.class);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.side_menu5).setOnClickListener(new View.OnClickListener() { //예약내역확인
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyBookingCheck.class);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.side_logout).setOnClickListener(new View.OnClickListener() { //로그아웃 !! -> 메인
            @Override
            public void onClick(View v) {
                pref= getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        editor.putString("logined_name", "");
                        editor.apply();

                        et_side_name = (EditText)findViewById(R.id.et_side_name);
                        et_side_name.setText("");

                        Intent intent = new Intent(getContext(), Main.class);
                        getContext().startActivity(intent);
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
    }

    @Override public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cross_btn1:
                listener.btnLevel1();
                break;

            default:
                break;
        }
    }

}