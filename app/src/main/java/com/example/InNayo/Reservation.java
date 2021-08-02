package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Reservation extends AppCompatActivity {
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "fname";

    JSONArray peoples=null;

    ArrayList<HashMap<String, String>> personList;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

            list = (ListView)findViewById(R.id.listview);
            personList = new ArrayList<HashMap<String, String>>();
            getData("http://172.30.1.41/PHP_facilities.php");
        }

        protected void showList(){
            try{
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i=0; i<peoples.length(); i++){
                    JSONObject c = peoples.getJSONObject(i);
                    String name = c.getString(TAG_NAME);

                    HashMap<String,String> persons = new HashMap<String, String>();

                    persons.put(TAG_NAME, name);

                    personList.add(persons);
                }
                ListAdapter adapter = new SimpleAdapter(
                        Reservation.this, personList, R.layout.list_item,
                        new String[]{TAG_NAME},
                        new int[]{R.id.name}
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