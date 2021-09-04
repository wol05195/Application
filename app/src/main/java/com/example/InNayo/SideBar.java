package com.example.InNayo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SideBar extends AppCompatActivity {

//    Intent intent;
//    RelativeLayout side_menu1, side_menu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);



//        side_menu1 = (RelativeLayout) findViewById(R.id.side_menu1);
//        side_menu2 = (RelativeLayout) findViewById(R.id.side_menu2);
//
//
//        RelativeLayout.OnClickListener onClickListener = new RelativeLayout.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.side_menu1:
//                        intent = new Intent(getApplicationContext(), Main.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.side_menu2:
//                        intent = new Intent(getApplicationContext(), Congestion.class);
//                        startActivity(intent);
//                        break;
//
//                }
//            }
//        };
    }
}

class SideBarView extends RelativeLayout implements View.OnClickListener {

    Intent intent;
    RelativeLayout side_menu1, side_menu2;

    /** 메뉴버튼 클릭 이벤트 리스너 */
    public EventListener listener;
    public void setEventListener(EventListener l) {
        listener = l; }
    /** 메뉴버튼 클릭 이벤트 리스너 인터페이스 */
    public interface EventListener {
        // 닫기 버튼 클릭 이벤트
        void btnCancel();
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
        findViewById(R.id.side_menu1).setOnClickListener(this);
        findViewById(R.id.side_menu2).setOnClickListener(this);
        findViewById(R.id.side_menu3).setOnClickListener(this);
        findViewById(R.id.side_menu4).setOnClickListener(this);
        findViewById(R.id.side_menu5).setOnClickListener(this);
        findViewById(R.id.side_logout).setOnClickListener(this);


    }
    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cross_btn1:
                listener.btnLevel1();
                break;
//
//            case R.id.side_menu1:
//                intent = new Intent(getApplicationContext(), Main.class);
//                startActivity(intent);
//                break;

            default:
                break;
        }
    }
}