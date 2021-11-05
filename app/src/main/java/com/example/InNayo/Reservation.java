package com.example.InNayo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
//한ㅇ희 되니 안되니 눈물나네ㅠ
public class Reservation extends AppCompatActivity implements View.OnClickListener {
    String DATE, TIME, RDATE, RTIME;
    Button reservation_bt1, reservation_bt2, reservation_bt3, reservation_bt4, reservation_bt5, reservation_bt6;
    TextView reservation_year, reservation_month, reservation_date, reservation_time, reservation_ap, reservation_people;
    EditText reservation_edit1;

    public static final String urls = "http://"+"192.168.1.63"+"/";
    private String TAG = "Reservation";
    private Context mContext = Reservation.this;

    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역

    private Boolean isMenuShow = false;
    private Boolean isExitFlag = false;

    JSONArray peoples=null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    ProgressDialog dialog = null;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    String[] searchresult, selectresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        init();
        addSideView();  //사이드바 add

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        reservation_year = (TextView) findViewById(R.id.reservation_year);
        reservation_month = (TextView)findViewById(R.id.reservation_month);
        reservation_date = (TextView)findViewById(R.id.reservation_date);
        reservation_time = (TextView)findViewById(R.id.reservation_time);
        reservation_ap = (TextView)findViewById(R.id.reservation_ap);
        reservation_people = (TextView)findViewById(R.id.reservation_people);

        String year = getIntent().getExtras().getString("year");
        reservation_year.setText(year);

        String month = getIntent().getExtras().getString("month");
        reservation_month.setText(month);

        String date = getIntent().getExtras().getString("date");
        reservation_date.setText(date);

        String time = getIntent().getExtras().getString("time");
        reservation_time.setText(time);

        String ap = getIntent().getExtras().getString("ap");
        reservation_ap.setText(ap + " ");

        String people = getIntent().getExtras().getString("people");
        reservation_people.setText(people);

        DATE = year + "-" + month + "-" + date;
        if(ap.equals("AM") == true){
            if (Integer.valueOf(time) <= 9) {
                TIME = "0" + time + ":00:00";
            }else{
                TIME = time + ":00:00";
            }
        }else{
            if(Integer.valueOf(time)==12){
                TIME = time + ":00:00";
            }else {
                TIME = String.valueOf(Integer.valueOf(time) + 12) + ":00:00";
            }
        }

        try{
            RDATE = URLEncoder.encode(DATE, "UTF-8");
            RTIME = URLEncoder.encode(TIME, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        reservation_bt1 = (Button)findViewById(R.id.reservation_bt1);
        reservation_bt2 = (Button)findViewById(R.id.reservation_bt2);
        reservation_bt3 = (Button)findViewById(R.id.reservation_bt3);
        reservation_bt4 = (Button)findViewById(R.id.reservation_bt4);
        reservation_bt5 = (Button)findViewById(R.id.reservation_bt5);
        reservation_bt6 = (Button)findViewById(R.id.reservation_bt6);
        reservation_edit1 = (EditText) findViewById(R.id.reservation_edit1);

        Button.OnClickListener onClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.reservation_bt1:
                        personList.clear();
                        dialog = ProgressDialog.show(Reservation.this,"","Validating user...",true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SelectDatabase(urls + "Facilities.php");
                            }
                        }).start();
                        break;
//                    case R.id.reservation_bt2:
//                        personList.clear();
//                        getData("http://172.30.1.49/Facilities_lectureroom.php");
//                        break;
//                    case R.id.reservation_bt3:
//                        personList.clear();
//                        getData("http://172.30.1.49/Facilities_library.php");
//                        break;
                    case R.id.reservation_bt4:
                        personList.clear();
                        dialog = ProgressDialog.show(Reservation.this,"","Validating user...",true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SelectDatabase(urls+"Facilities_cafe.php");
                            }
                        }).start();
                        break;
                    case R.id.reservation_bt5:
                        personList.clear();
                        dialog = ProgressDialog.show(Reservation.this,"","Validating user...",true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SelectDatabase(urls+"Facilities_restaurant.php");
                            }
                        }).start();
                        break;
                    case R.id.reservation_bt6:
                        personList.clear();
                        dialog = ProgressDialog.show(Reservation.this,"","Validating user...",true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SearchDatabase();
                            }
                        }).start();
                        break;
                }
            }
        };
        reservation_bt1.setOnClickListener(onClickListener);
        reservation_bt2.setOnClickListener(onClickListener);
        reservation_bt3.setOnClickListener(onClickListener);
        reservation_bt4.setOnClickListener(onClickListener);
        reservation_bt5.setOnClickListener(onClickListener);
        reservation_bt6.setOnClickListener(onClickListener);

        list = (ListView)findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();
        dialog = ProgressDialog.show(Reservation.this,"","Validating user...",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SelectDatabase(urls+"Facilities.php");
            }
        }).start();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String sp = String.valueOf(arg0.getAdapter().getItem(arg2));
                if (sp.indexOf("=")>=1){
                    sp = sp.substring(sp.indexOf("=")+1,sp.indexOf("}"));
                }
                Intent intent = new Intent(Reservation.this, ReservationCheck.class);
                intent.putExtra("selectedplace", sp);
                intent.putExtra("selectedyear", year);
                intent.putExtra("selectedmonth", month);
                intent.putExtra("selecteddate", date);
                intent.putExtra("selectedhour", time);
                intent.putExtra("selectedap", ap);
                intent.putExtra("selectedpp", people);
                startActivity(intent);

            }
        });

    }

    private void init(){

        findViewById(R.id.btn_menu).setOnClickListener(this);

        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);

    }

    private void addSideView() {
        SideBarView sidebar = new SideBarView(mContext);
        sideLayout.addView(sidebar);

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sidebar.setEventListener(new SideBarView.EventListener() {

            @Override
            public void btnLevel1() {
                Log.e(TAG, "btnLevel1");

                closeMenu();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_menu:

                showMenu();
                break;
        }
    }

    public void closeMenu() {

        isMenuShow = false;
        Animation slide = AnimationUtils.loadAnimation(mContext, R.anim.sidebar_hidden);
        sideLayout.startAnimation(slide);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        }, 450);
    }

    public void showMenu() {

        isMenuShow = true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
        Log.e(TAG, "메뉴버튼 클릭");
    }
    void SelectDatabase(String link) {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(link);
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("Rdate", RDATE));
            nameValuePairs.add(new BasicNameValuePair("Rtime", RTIME));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response2 = httpclient.execute(httppost, responseHandler);
            String res = response2.replace('"', ' ').replace("[", "").replace("]","").replace(" ","");
            selectresult = res.split(",");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            if (response2.equalsIgnoreCase("No Such User Found")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(Reservation.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(Reservation.this, android.R.layout.simple_list_item_1, selectresult);
                        list.setAdapter(itemsAdapter);
                    }
                });
            }
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    void SearchDatabase() {
        try {
            String data = URLEncoder.encode(reservation_edit1.getText().toString(), "UTF-8");
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(urls+"Facilities_search.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("SearchItem", data));
            nameValuePairs.add(new BasicNameValuePair("Rdate", RDATE));
            nameValuePairs.add(new BasicNameValuePair("Rtime", RTIME));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            String res = response.replace('"', ' ').replace("[", "").replace("]","").replace(" ","");
            searchresult = res.split(",");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            if (response.equalsIgnoreCase("No Such User Found")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(Reservation.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(Reservation.this, android.R.layout.simple_list_item_1, searchresult);
                        list.setAdapter(itemsAdapter);
                    }
                });
            }
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
}