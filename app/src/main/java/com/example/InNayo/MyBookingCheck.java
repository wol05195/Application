package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MyBookingCheck extends AppCompatActivity {
    String myJSON;
    JSONArray peoples=null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    private static final String TAG_RESULTS = "result", TAG_DATE = "rdate",
            TAG_TIME = "rtime", TAG_PEOPLE = "enter_count", TAG_NAME = "fname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_check);

        list = (ListView)findViewById(R.id.listview);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.35.58/My_Booking_Check.php");
    }
    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<peoples.length(); i++){
                JSONObject c = peoples.getJSONObject(i);
                String list_item_date = c.getString(TAG_DATE);
                String list_item_time = c.getString(TAG_TIME);
                String list_item_people = c.getString(TAG_PEOPLE);
                String list_item_name = c.getString(TAG_NAME);
                HashMap<String,String> persons = new HashMap<String, String>();

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