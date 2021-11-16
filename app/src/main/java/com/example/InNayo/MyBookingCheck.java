package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
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
    String myJSON, logined_name;
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;

    private static final String TAG_RESULTS = "result", TAG_DATE = "rdate",
            TAG_TIME = "rtime", TAG_PEOPLE = "enter_count", TAG_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_check);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name", "");

        list = (ListView) findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();
        getData(urls+"My_Booking_Check.php", logined_name.replace(" ", ""));
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
}