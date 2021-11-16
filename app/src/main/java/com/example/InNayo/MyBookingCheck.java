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

        list = (ListView) findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name", "");

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


    private void insertToDatabases(String Category, String Name, String Location, String Phone, String TotalPerson, String StartTime, String EndTime) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Administrator.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast toast = Toast.makeText(Administrator.this, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String Category = (String) params[0];
                    String Name = (String) params[1];
                    String Location = (String) params[2];
                    String Phone = (String) params[3];
                    String TotalPerson = (String) params[4];
                    String StartTime = (String) params[5];
                    String EndTime = (String) params[6];

                    String link = urls+"My_Booking_Check.php";
                    String data = URLEncoder.encode("Category", "UTF-8") + "=" + URLEncoder.encode(Category, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Location", "UTF-8") + "=" + URLEncoder.encode(Location, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
                    data += "&" + URLEncoder.encode("TotalPerson", "UTF-8") + "=" + URLEncoder.encode(TotalPerson, "UTF-8");
                    data += "&" + URLEncoder.encode("StartTime", "UTF-8") + "=" + URLEncoder.encode(StartTime, "UTF-8");
                    data += "&" + URLEncoder.encode("EndTime", "UTF-8") + "=" + URLEncoder.encode(EndTime, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Category, Name, Location, Phone, TotalPerson, StartTime, EndTime);
    }
}