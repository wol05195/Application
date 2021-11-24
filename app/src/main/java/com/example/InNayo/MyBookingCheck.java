package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;



import static com.example.InNayo.Reservation.urls;

public class MyBookingCheck extends AppCompatActivity {
    String myJSON, logined_name, rfname, rtime, rdate, rcount, list_item_time_sub;
    ArrayList<Integer> index = new ArrayList<Integer>();
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;

    private static final String TAG_RESULTS = "result", TAG_DATE = "rdate",
            TAG_TIME = "rtime", TAG_PEOPLE = "enter_count", TAG_NAME = "fname";

    Button btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_check);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name", "");
        logined_name =logined_name.replace(" ", "");

        list = (ListView) findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();
        getData(urls+"My_Booking_Check.php", logined_name);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String Ritem = String.valueOf(arg0.getAdapter().getItem(arg2));
                if (Ritem.indexOf("=")>=1){
                    index.clear();
                    index.add(Ritem.indexOf("="));
                    index.add(Ritem.indexOf(","));
                    index.add(Ritem.indexOf("=", index.get(0)+1));
                    index.add(Ritem.indexOf(",", index.get(1)+1));
                    index.add(Ritem.indexOf("=", index.get(2)+1));
                    index.add(Ritem.indexOf(",", index.get(3)+1));
                    index.add(Ritem.indexOf("=", index.get(4)+1));
                    rfname = Ritem.substring(index.get(0)+1, index.get(1));
                    rtime = Ritem.substring(index.get(2)+1, index.get(3));
                    rcount = Ritem.substring(index.get(4)+1, index.get(5));
                    rdate = Ritem.substring(index.get(6)+1, Ritem.indexOf("}"));
                }
//                Toast.makeText(MyBookingCheck.this, rdate, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MyBookingCheck.this);
                builder.setMessage("예약을 취소 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                            removeData(rfname, rdate, rtime, rcount);
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

        btn_menu = (Button)findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });
    }


    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);


            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String list_item_date = c.getString(TAG_DATE);
                String list_item_time = c.getString(TAG_TIME);
                String list_item_people = c.getString(TAG_PEOPLE);
                String list_item_name = c.getString(TAG_NAME);
                HashMap<String, String> persons = new HashMap<String, String>();

//                if(Integer.valueOf(list_item_time.substring(0,2)) == 12){
//                    list_item_time_sub = "오후 "+ list_item_time.substring(0,2) +"시";
//                }else if(Integer.valueOf(list_item_time.substring(0,2)) >= 13){
//                    list_item_time_sub = "오후 "+ String.valueOf(Integer.valueOf(list_item_time.substring(0,2))-12) +"시";
//                }else{
//                    list_item_time_sub = "오전 "+ list_item_time.substring(1,2) +"시";
//                }

                persons.put(TAG_DATE, list_item_date);
                persons.put(TAG_TIME, list_item_time);
                persons.put(TAG_PEOPLE, list_item_people);
                persons.put(TAG_NAME, list_item_name);

                personList.add(persons);
            }
            ListAdapter adapter = new SimpleAdapter(
                    MyBookingCheck.this, personList, R.layout.list_item,
                    new String[]{TAG_DATE, TAG_TIME, TAG_PEOPLE, TAG_NAME},
                    new int[]{R.id.list_item_date, R.id.list_item_time, R.id.list_item_people, R.id.list_item_name}

            );
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getData(String url, String Lname) {
        class GetDataJson extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try{
                String uri = params[0];
                String Lname = params[1];

                BufferedReader bufferedReader = null;

                String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Lname, "UTF-8");

                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                wr.write(data);
                wr.flush();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String json;
                while ((json = bufferedReader.readLine()) != null){
                    sb.append(json + '\n');
                    break;
                }
                return sb.toString().trim();
                } catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result){
                myJSON = result;
                showList();
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url, Lname);
    }

    public void removeData(String Rfname, String Rdate, String Rtime, String Rcount) {
        class GetDataJson extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try{
                    String Rfname = params[0];
                    String Rdate = params[1];
                    String Rtime = params[2];
                    String Rcount = params[3];

                    BufferedReader bufferedReader = null;

                    String data = URLEncoder.encode("Rname", "UTF-8") + "=" + URLEncoder.encode(logined_name, "UTF-8");
                    data += "&" + URLEncoder.encode("Rfname", "UTF-8") + "=" + URLEncoder.encode(Rfname, "UTF-8");
                    data += "&" + URLEncoder.encode("Rdate", "UTF-8") + "=" + URLEncoder.encode(Rdate, "UTF-8");
                    data += "&" + URLEncoder.encode("Rtime", "UTF-8") + "=" + URLEncoder.encode(Rtime, "UTF-8");
                    data += "&" + URLEncoder.encode("Rcount", "UTF-8") + "=" + URLEncoder.encode(Rcount, "UTF-8");

                    URL url = new URL(urls+"My_Booking_Remove.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                    wr.write(data);
                    wr.flush();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String json;
                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json + '\n');
                        break;
                    }
                    return sb.toString().trim();
                } catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result){
                Toast.makeText(MyBookingCheck.this, result , Toast.LENGTH_SHORT).show();
                if(result.equals("예약 취소 완료")){
                    refresh();
                }

            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(Rfname, Rdate, Rtime, Rcount);
    }
    public void refresh(){
        startActivity(new Intent(this, MyBookingCheck.class));
        overridePendingTransition(0, 0);
    }
}