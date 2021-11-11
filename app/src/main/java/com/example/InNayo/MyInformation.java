package com.example.InNayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.InNayo.Reservation.urls;

public class MyInformation extends AppCompatActivity {
    EditText my_information_et0, my_information_et1, my_information_et2, my_information_et3, my_information_et4, my_information_et5;
    Button my_information_bt, my_information_bt2;
    ProgressDialog dialog = null;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    String[] searchresult;
    SharedPreferences pref;          // 프리퍼런스
    SharedPreferences.Editor editor;
    String logined_name;
    EditText et_side_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        my_information_et1 = (EditText) findViewById(R.id.my_information_et1);
        my_information_et2 = (EditText) findViewById(R.id.my_information_et2);
        my_information_et3 = (EditText) findViewById(R.id.my_information_et3);
        my_information_et4 = (EditText) findViewById(R.id.my_information_et4);
        my_information_et5 = (EditText) findViewById(R.id.my_information_et5);


//        my_information_bt2 = findViewById(R.id.my_information_bt2);
//        my_information_bt2.setOnClickListener(v -> {
//            dialog = ProgressDialog.show(MyInformation.this,"","Validating user...",true);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    GetDatabase();
//                }
//            }).start();
//        });
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        logined_name = pref.getString("logined_name","");

        dialog = ProgressDialog.show(MyInformation.this,"","Validating user...",true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    GetDatabase();
                }
            }).start();

        my_information_bt = findViewById(R.id.my_information_bt);
        my_information_bt.setOnClickListener(v -> {
            String Name = my_information_et1.getText().toString();
            String Id = my_information_et2.getText().toString();
            String PassWord = my_information_et3.getText().toString();
            String Email = my_information_et4.getText().toString();
            String Phone = my_information_et5.getText().toString();

            UpdateDatabases(Name, Id, PassWord, Email, Phone);

            Toast toast = Toast.makeText(getApplicationContext(), "개인정보가 수정되었습니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 300);
            toast.show();
        });

    }
    void GetDatabase() {
        try {
            String data = URLEncoder.encode(logined_name.replace(" ", ""), "UTF-8");
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(urls+"My_Information_getdata.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("Name", data));
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
                        Toast.makeText(MyInformation.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        my_information_et1.setText(searchresult[2]);
                        my_information_et2.setText(searchresult[0]);
                        my_information_et3.setText(searchresult[1]);
                        my_information_et4.setText(searchresult[4]);
                        my_information_et5.setText(searchresult[3]);
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

    private void UpdateDatabases(String Name, String Id, String PassWord, String Email, String Phone) {
        class UpdateData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MyInformation.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
//                Toast toast = Toast.makeText(MyInformation.this, s, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 30);
//                toast.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    String Name = (String) params[0];
                    String Id = (String) params[1];
                    String PassWord = (String) params[2];
                    String Email = (String) params[3];
                    String Phone = (String) params[4];

                    String link = urls+"My_information_update.php";
                    String data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("PassWord", "UTF-8") + "=" + URLEncoder.encode(PassWord, "UTF-8");
                    data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");

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
        UpdateData task = new UpdateData();
        task.execute(Name, Id, PassWord, Email, Phone);
    }
}