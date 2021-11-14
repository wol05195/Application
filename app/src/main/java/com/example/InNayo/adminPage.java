package com.example.InNayo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


//로그인 성공시
public class adminPage extends AppCompatActivity {
//    TextView idText, pswordText, welcome;
//    Button manageMentButton;
//    String userID, userPassword,message;
//    StringBuffer stringBuffer;

    Button a_reservation, a_users;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        a_reservation = (Button)findViewById(R.id.admin_reservationButton);
        a_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), adminReservation.class);
                startActivity(intent);
            }
        });
        a_users = (Button)findViewById(R.id.admin_memberButton);
        a_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), adminMember.class);
                startActivity(intent);
            }
        });
    }
}
//        idText =(TextView)findViewById(R.id.idText);
//        pswordText =(TextView)findViewById(R.id.pswordText);
//        welcome=(TextView)findViewById(R.id.welcome);
//
//        manageMentButton=(Button)findViewById(R.id.manageMentButton);
//        manageMentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new BackgroundTask().execute();
//            }
//        });
//
//
//        Intent intent=getIntent();
//        if(intent!=null){
//            userID=intent.getStringExtra("userID");
//            userPassword=intent.getStringExtra("userPassword");
//            message ="환영 합니다. " + userID + "님!";
//
//            idText.setText(userID);
//            pswordText.setText(userPassword);
//            welcome.setText(message);
//        }
//
//
//        if(!userID.equals("admin")){
//            manageMentButton.setVisibility(View.GONE);
//        }
//
//
//    }
//
//
//    //실제 회원 목록 가져오기,, AsyncTask 이용 또는 스레드 사용
//
//    class BackgroundTask extends AsyncTask<Void, Void,  String>{
//
//        String target;
//
//        @Override
//        protected void onPreExecute() {
//            target="http://braverokmc2.dothome.co.kr/List.php";
//        }
//
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            InputStream is=null;
//            InputStreamReader isr =null;
//            BufferedReader reader=null;
//            stringBuffer =new StringBuffer();
//
//
//            try{
//
//                URL url =new URL(target);
//                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
//
//                httpURLConnection.setConnectTimeout(10000);
//                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
//
//                    is=httpURLConnection.getInputStream();
//                    reader=new BufferedReader(new InputStreamReader(is));
//
//                    while(true){
//                        String stringLine =reader.readLine();
//                        if(stringLine==null)break;
//                        stringBuffer.append(stringLine+"\n");
//                    }
//
//                }
//
//                parsing(stringBuffer.toString());
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                try {
//                    if(reader!=null) reader.close();
//                    if(isr!=null) isr.close();
//                    if(is!=null)is.close();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//            return null;
//        }
//
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            Intent intent =new Intent(adminPage.this, adminMember.class);
///*
//
//            Bundle bundle =new Bundle();
//            bundle.putSerializable("members", members);
//            intent.putExtra("members",  bundle);
//*//*
//
//
//            //
//            intent.putExtra("members", stringBuffer.toString());
//            intent.putExtra("saveList", stringBuffer.toString());
//            startActivity(intent);
//
//        }
//
//    }
//
//
//    public void parsing(String data){
//
//        members=new ArrayList<>();
//        try{
//            JSONObject jsonObject =new JSONObject(data);
//            JSONArray jsonArray=new JSONArray(jsonObject.getString("response"));
//
//            //arrayList 클리어
//            members.clear();
//
//            for(int i=0; i<jsonArray.length(); i++){
//
//                MemberDTO member=new MemberDTO();
//
//                JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
//                member.setUserID(jsonObject1.getString("userID"));
//                member.setUserName(jsonObject1.getString("userName"));
//                member.setUserPassword(jsonObject1.getString("userPassword"));
//                member.setUserAge(jsonObject1.getInt("userAge"));
//
//                if(!member.getUserID().equals("admin")) {
//                    members.add(member);
//                }
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//*/






