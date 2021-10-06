package com.example.InNayo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Reservation extends AppCompatActivity {
    String myJSON;
    Button reservation_bt1, reservation_bt2, reservation_bt3, reservation_bt4, reservation_bt5;
    TextView reservation_year, reservation_month, reservation_date, reservation_time, reservation_ap, reservation_people;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "fname";

    JSONArray peoples=null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

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

        reservation_bt1 = (Button)findViewById(R.id.reservation_bt1);
        reservation_bt2 = (Button)findViewById(R.id.reservation_bt2);
        reservation_bt3 = (Button)findViewById(R.id.reservation_bt3);
        reservation_bt4 = (Button)findViewById(R.id.reservation_bt4);
        reservation_bt5 = (Button)findViewById(R.id.reservation_bt5);

        Button.OnClickListener onClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.reservation_bt1:
                        personList.clear();
                        getData("http://192.168.35.240/Facilities.php");
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
                        getData("http://192.168.35.240/Facilities_cafe.php");
                        break;
                    case R.id.reservation_bt5:
                        personList.clear();
                        getData("http://192.168.35.240/Facilities_restaurant.php");
                        break;
                }
            }
        };
        reservation_bt1.setOnClickListener(onClickListener);
        reservation_bt2.setOnClickListener(onClickListener);
        reservation_bt3.setOnClickListener(onClickListener);
        reservation_bt4.setOnClickListener(onClickListener);
        reservation_bt5.setOnClickListener(onClickListener);

        list = (ListView)findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.35.240/Facilities.php");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String sp = String.valueOf(arg0.getAdapter().getItem(arg2));
                sp = sp.substring(sp.indexOf("=")+1,sp.indexOf("}"));
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

    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                String list_item_name = c.getString(TAG_NAME);

                HashMap<String,String> persons = new HashMap<String, String>();

                persons.put(TAG_NAME, list_item_name);

                personList.add(persons);
            }
            ListAdapter adapter = new SimpleAdapter(
                    Reservation.this, personList, R.layout.list_item_reservation,
                    new String[]{TAG_NAME},
                    new int[]{R.id.list_item_name}
            );
            list.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void getData(String url) {
        class GetDataJson extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json + '\n');
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
        g.execute(url);

    }
}