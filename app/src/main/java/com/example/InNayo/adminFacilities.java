package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.InNayo.Reservation.urls;

public class adminFacilities extends AppCompatActivity {
    String myJSON, Fname;
    JSONArray reservation=null;

    ArrayList<HashMap<String, String>> reservationlist;
    ListView list;

    private static final String TAG_RESULTS = "result",  TAG_NAME = "fname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_facilities);

        list = (ListView)findViewById(R.id.listview);
        reservationlist = new ArrayList<HashMap<String, String>>();
        getData(urls+"adminfacilities.php");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Fname = String.valueOf(arg0.getAdapter().getItem(arg2));
                if (Fname.indexOf("=")>=1){
                    Fname = Fname.substring(Fname.indexOf("=")+1, Fname.indexOf("}"));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(adminFacilities.this);
                builder.setMessage("등록된 시설 삭제하기");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removefacilities(Fname);
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
    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            reservation = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<reservation.length(); i++){
                JSONObject c = reservation.getJSONObject(i);
                String list_item_name = c.getString(TAG_NAME);
                HashMap<String,String> reservations = new HashMap<String, String>();

                reservations.put(TAG_NAME, list_item_name);

                reservationlist.add(reservations);
            }
            ListAdapter adapter = new SimpleAdapter(
                    adminFacilities.this, reservationlist, R.layout.list_item_admin_facilities,
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

    public void removefacilities(String FNAME) {
        class GetDataJson extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try{
                    String fname = params[0];

                    BufferedReader bufferedReader = null;

                    String data = URLEncoder.encode("FNAME", "UTF-8") + "=" + URLEncoder.encode(fname.trim(), "UTF-8");

                    URL url = new URL(urls+"admin_facilities_remove.php");
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
                Toast.makeText(adminFacilities.this, result , Toast.LENGTH_SHORT).show();
                if(result.equals("시설 삭제 완료")){
                    refresh();
                }

            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(FNAME);
    }
    public void refresh(){
        startActivity(new Intent(this, adminFacilities.class));
        overridePendingTransition(0, 0);
    }
}