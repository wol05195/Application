package com.example.InNayo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.InNayo.Reservation.urls;

public class adminMember extends AppCompatActivity {
    String myJSON;
    JSONArray members = null;

    ArrayList<HashMap<String, String>> memberList;
    ListView list;

    private static final String TAG_RESULT = "result", TAG_ID = "uid",
            TAG_PW = "upw", TAG_NAME = "uname", TAG_PHONE = "uphone", TAG_EM = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_member);

        list = findViewById(R.id.listview);
        memberList = new ArrayList<HashMap<String, String>>();
        getData(urls + "adminmember_a.php");
    }

    protected void showList() {
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            members = jsonObj.getJSONArray(TAG_RESULT);

            for(int i = 0; i < members.length(); i++) {
                JSONObject c = members.getJSONObject(i);
                String list_item_id = c.getString(TAG_ID);
                String list_item_password = c.getString(TAG_PW);
                String list_item_phone = c.getString(TAG_PHONE);
                String list_item_name = c.getString(TAG_NAME);
                String list_item_email = c.getString(TAG_EM);
                HashMap<String, String> members = new HashMap<String, String>();

                members.put(TAG_ID, list_item_id);
                members.put(TAG_PW, list_item_password);
                members.put(TAG_PHONE, list_item_phone);
                members.put(TAG_NAME, list_item_name);
                members.put(TAG_EM, list_item_email);

                memberList.add(members);
            }
            ListAdapter adapter = new SimpleAdapter(
                    adminMember.this, memberList, R.layout.list_admin_member,
                    new String[]{TAG_ID, TAG_PW, TAG_NAME, TAG_PHONE, TAG_EM},
                    new int[]{R.id.list_item_id, R.id.list_item_password,R.id.list_item_name, R.id.list_item_phone, R.id.list_item_email}
            );
            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
    }
}
        public void getData (String url){
            class GetDataJson extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {
                    String uri = params[0];

                    BufferedReader bufferedReader = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + '\n');
                        }
                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }

                }

                @Override
                protected void onPostExecute(String result) {
                    myJSON = result;
                    showList();
                }
            }
            GetDataJson g = new GetDataJson();
            g.execute(url);


        }
    }

